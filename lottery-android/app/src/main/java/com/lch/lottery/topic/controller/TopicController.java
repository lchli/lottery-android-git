package com.lch.lottery.topic.controller;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lch.lottery.DI;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.TopicResponse;
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
    private Callback callback;


    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void getAllTopicsAndTag() {
        getTopicsImpl(null, null, null, null, null, null);
    }

    public void getMyTopics() {
        ResponseValue<UserResponse.User> res = localUserRepo.getCurrentUser();
        if (res.data == null) {
            onFail("未登录");
            return;
        }

        getTopicsImpl(null, null, null, null, null, res.data.userId);
    }


    private void getTopicsImpl(final String sort, final String sortDirect,
                               final String tag, final String title,
                               final String topicId, final String userId) {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                final ResponseValue<List<TopicResponse.Topic>> res = topicRepo.getTopics(sort, sortDirect, tag,
                        title, topicId, userId);
                if (res == null) {
                    onFail("res is null");
                    return;
                }

                if (res.err != null) {
                    onFail(res.err.msg);
                    // return;
                }

                final List<Object> datas = new ArrayList<>();

                AdResponse adRes = new AdResponse();
                adRes.data = new ArrayList<>();

                AdResponse.Ad ad = new AdResponse.Ad();
                ad.imgUrl = "https://www.baidu.com/img/bd_logo1.png";
                adRes.data.add(ad);

                ad = new AdResponse.Ad();
                ad.imgUrl = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3756930651,2591929300&fm=173&s=A4006DB54A23149C5F9981060300D0C1&w=218&h=146&img.JPEG";
                adRes.data.add(ad);

                datas.add(adRes);
                datas.add(new Object());

                if (res.data != null) {
                    datas.addAll(res.data);
                }

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
