package com.lch.lottery.topic;


import com.lch.netkit.NetKit;
import com.lch.netkit.string.Callback;
import com.lch.netkit.string.Parser;
import com.lch.netkit.string.StringRequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface TopicListContract {


    interface View {

        void onLoadedTopicList(List<Object> datas);

        void onLoadFail(String msg);

    }

    interface Presenter {

        void registerView(View view);

        void unregisterView();

        void loadTopicList();
    }

    class PresenterImpl implements Presenter {

        private View view;

        @Override
        public void registerView(View view) {
            this.view = view;
        }

        @Override
        public void unregisterView() {
            this.view = null;
        }

        @Override
        public void loadTopicList() {
            StringRequestParams params = new StringRequestParams()
                    .setUrl("http://www.baidu.com")
                    .addParam("", "");

            NetKit.stringRequest().get(params, new Parser<List<Object>>() {
                @Override
                public List<Object> parse(String s) {
                    List<TopicModel> topics = new ArrayList<>();
                    TopicModel topic = new TopicModel();
                    topic.topicTag = "1";
                    topics.add(topic);
                    topic = new TopicModel();
                    topic.topicTag = "2";
                    topics.add(topic);

                    Collections.sort(topics, new Comparator<TopicModel>() {
                        @Override
                        public int compare(TopicModel o1, TopicModel o2) {
                            if (o1.topicTag == null || o2.topicTag == null) {
                                return 0;
                            }
                            return o1.topicTag.compareTo(o2.topicTag);
                        }
                    });

                    List<Object> datas = new ArrayList<>();

                    String preTag = null;

                    for (TopicModel top : topics) {
                        if (top.topicTag != null && !top.topicTag.equals(preTag)) {
                            datas.add(new TopicSection(TopicListAdapter.TYPE_PIN, top.topicTag));
                        }
                        datas.add(top);
                        preTag = top.topicTag;
                    }

                    return datas;
                }
            }, new Callback<List<Object>>() {
                @Override
                public void onSuccess(List<Object> objects) {
                    if (view != null) {
                        view.onLoadedTopicList(objects);
                    }

                }

                @Override
                public void onFail(String s) {
                    if (view != null) {
                        view.onLoadFail(s);
                    }
                }
            });

        }
    }

}
