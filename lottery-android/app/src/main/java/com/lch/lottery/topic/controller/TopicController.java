package com.lch.lottery.topic.controller;

import android.support.annotation.NonNull;

import com.lch.lottery.topic.TopicListAdapter;
import com.lch.lottery.topic.TopicSection;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.topic.repository.RepoInjector;
import com.lch.lottery.topic.repository.TopicRepo;
import com.lch.netkit.NetKit;
import com.lch.netkit.string.ResponseValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    private TopicRepo topicRepo = RepoInjector.injectTopicRepo();
    private Callback callback;


    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public void getAllTopicsAndTag() {

        NetKit.runAsync(new Runnable() {
            @Override
            public void run() {
                final ResponseValue<List<TopicResponse.Topic>> res = topicRepo.getAllTopics();
                if (res == null) {
                    return;
                }

                if (res.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFail(res.err.msg);
                            }
                        }
                    });
                    return;
                }

                if (res.data == null || res.data.isEmpty()) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onGet(null);
                            }
                        }
                    });
                    return;
                }

                Collections.sort(res.data, new Comparator<TopicResponse.Topic>() {
                    @Override
                    public int compare(TopicResponse.Topic o1, TopicResponse.Topic o2) {
                        if (o1.tag == null || o2.tag == null) {
                            return 0;
                        }
                        return o1.tag.compareTo(o2.tag);
                    }
                });

                final List<Object> datas = new ArrayList<>();

                String preTag = null;

                for (TopicResponse.Topic top : res.data) {
                    if (top.tag != null && !top.tag.equals(preTag)) {
                        datas.add(new TopicSection(TopicListAdapter.TYPE_PIN, top.tag));
                    }
                    datas.add(top);

                    preTag = top.tag;
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
                final ResponseValue res = topicRepo.addOrUpdateTopic(topic);
                if (res == null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFail("res is null");
                            }
                        }
                    });

                    return;
                }

                if (res.err != null) {
                    NetKit.runInUI(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFail(res.err.msg);
                            }
                        }
                    });
                    return;

                }

                NetKit.runInUI(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                    }
                });
            }
        });


    }

}
