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

    public interface Q {
        void onGet(List<Object> data);


        void onFail(String msg);

        void onNoMore();
    }

    public interface C {
        void onSuccess();

        void onFail(String msg);

    }


    private TopicRepo topicRepo = DI.injectTopicRepo();
    private UserRepo localUserRepo = DI.injectLocalUserRepo();
    private AdRepo adRepo = DI.injectAdRepo();
    private NoticeRepo noticeRepo = DI.injectNoticeRepo();

    private int page = 0;
    private static final int PAGE_SIZE = 50;
    private List<TopicResponse.Topic> totalTopics = new ArrayList<>();
    private boolean isHaveMore = true;


    public void getMyTopics(final Q callback) {

        ResponseValue<UserResponse.User> res = localUserRepo.getCurrentUser();

        if (res.data == null) {
            NetKit.runInUI(new Runnable() {
                @Override
                public void run() {
                    callback.onFail("未登录");
                }
            });
            return;
        }

        getTopics(null, null, null, null, null, res.data.userId, callback);
    }

    public void getTopics(final String sort, final String sortDirect,
                          final String tag, final String title,
                          final String topicId, final String userId, Q callback) {

        page = 0;
        isHaveMore = true;
        totalTopics.clear();

        getTopicsImpl(sort, sortDirect, tag, title, topicId, userId, callback);
    }


    private void getTopicsImpl(final String sort, final String sortDirect,
                               final String tag, final String title,
                               final String topicId, final String userId, @NonNull final Q callback) {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                final ResponseValue<List<TopicResponse.Topic>> topicRes = topicRepo.getTopics(sort, sortDirect, tag,
                        title, topicId, userId, page, PAGE_SIZE);
                if (topicRes.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(topicRes.err.msg);
                        }
                    });
                }

                final ResponseValue<AdResponse> adRes = adRepo.getAd();
                if (adRes.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(adRes.err.msg);
                        }
                    });
                }

                final ResponseValue<NoticeResponse> noticeRes = noticeRepo.getNotice();
                if (noticeRes.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(noticeRes.err.msg);
                        }
                    });
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
                        callback.onGet(datas);
                    }
                });

            }
        });


    }


    public void loadMore(final String sort, final String sortDirect,
                         final String tag, final String title,
                         final String topicId, final String userId, @NonNull final Q callback) {
        if (!isHaveMore) {
            NetKit.runInUI(new Runnable() {
                @Override
                public void run() {
                    callback.onNoMore();
                }
            });
            return;
        }

        getTopicsImpl(sort, sortDirect, tag, title, topicId, userId, callback);
    }


    public void addOrUpdateTopic(@NonNull final TopicResponse.Topic topic, @NonNull final C callback) {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(topic.title)) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail("标题不能为空");
                        }
                    });
                    return;
                }

                if (TextUtils.isEmpty(topic.content)) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail("内容不能为空");
                        }
                    });
                    return;
                }

                final ResponseValue res = topicRepo.addOrUpdateTopic(topic);

                if (res.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(res.err.msg);
                        }
                    });
                    return;

                }

                NetKit.runInUI(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess();
                    }
                });
            }
        });


    }


}
