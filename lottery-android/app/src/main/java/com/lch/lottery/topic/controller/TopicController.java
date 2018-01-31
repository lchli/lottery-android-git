package com.lch.lottery.topic.controller;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.EmptyUtils;
import com.lch.lottery.DI;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.topic.repository.AdRepo;
import com.lch.lottery.topic.repository.NoticeRepo;
import com.lch.lottery.topic.repository.TopicRepo;
import com.lch.lottery.user.model.UserResponse;
import com.lch.lottery.user.repository.UserRepo;
import com.lch.netkit.NetKit;
import com.lch.netkit.string.ResponseValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

public class TopicController {

    public static abstract class Callback {
        public void onGet(List<Object> data) {
        }

        public void onFail(String msg) {
        }

        public void onSuccess() {
        }
    }

    private TopicRepo topicRepo = DI.injectTopicRepo();
    private UserRepo localUserRepo = DI.injectLocalUserRepo();
    private AdRepo adRepo = DI.injectAdRepo();
    private NoticeRepo noticeRepo = DI.injectNoticeRepo();

    private Callback callback;
    private int page = 0;
    private static final int PAGE_SIZE = 50;
    private List<TopicResponse.Topic> totalTopics = new ArrayList<>();
    private boolean isHaveMore = true;


    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public void getMyTopics() {
        ResponseValue<UserResponse.User> res = localUserRepo.getCurrentUser();
        if (res.data == null) {
            onFail("未登录");
            return;
        }

        getTopics(null, null, null, null, null, res.data.userId);
    }

    public void getTopics(final String sort, final String sortDirect,
                          final String tag, final String title,
                          final String topicId, final String userId) {

        page = 0;
        isHaveMore = true;
        totalTopics.clear();

        getTopicsImpl(sort, sortDirect, tag, title, topicId, userId);
    }


    private void getTopicsImpl(final String sort, final String sortDirect,
                               final String tag, final String title,
                               final String topicId, final String userId) {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                final ResponseValue<List<TopicResponse.Topic>> topicRes = topicRepo.getTopics(sort, sortDirect, tag,
                        title, topicId, userId, page, PAGE_SIZE);
                if (topicRes.err != null) {
                    onFail(topicRes.err.msg);
                }

                ResponseValue<AdResponse> adRes = adRepo.getAd();
                if (adRes.err != null) {
                    onFail(adRes.err.msg);
                }

                ResponseValue<NoticeResponse> noticeRes = noticeRepo.getNotice();
                if (noticeRes.err != null) {
                    onFail(noticeRes.err.msg);
                }
//

                if (adRes.data != null && EmptyUtils.isNotEmpty(adRes.data.data)) {
                    datas.add(adRes.data);
                }

                if (noticeRes.data != null && EmptyUtils.isNotEmpty(noticeRes.data.data)) {
                    datas.add(noticeRes.data);
                }

                if (EmptyUtils.isNotEmpty(topicRes.data)) {
                    totalTopics.addAll(topicRes.data);
                }

                if (topicRes.err == null) {//no err.
                    if (EmptyUtils.isEmpty(topicRes.data) || topicRes.data.size() < PAGE_SIZE) {
                        isHaveMore = false;
                    } else {
                        page++;
                    }
                }

                datas.addAll(totalTopics);

                NetKit.runInUI(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onGet(datas);
                        }
                    }
                });

            }
        });


    }


    public void loadMore(final String sort, final String sortDirect,
                         final String tag, final String title,
                         final String topicId, final String userId) {
        if (!isHaveMore) {
            return;
        }

        getTopicsImpl(sort, sortDirect, tag, title, topicId, userId);
    }


    public void addOrUpdateTopic(@NonNull final TopicResponse.Topic topic) {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(topic.title)) {
                    onFail("标题不能为空");
                    return;
                }

                if (TextUtils.isEmpty(topic.content)) {
                    onFail("内容不能为空");
                    return;
                }

                final ResponseValue res = topicRepo.addOrUpdateTopic(topic);
                if (res == null) {
                    onFail("res is null");
                    return;
                }

                if (res.err != null) {
                    onFail(res.err.msg);
                    return;

                }

                onSuccess();
            }
        });


    }


    private void onFail(final String msg) {

        NetKit.runInUI(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFail(msg);
                }
            }
        });
    }

    private void onSuccess() {
        NetKit.runInUI(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess();
                }
            }
        });
    }

}
