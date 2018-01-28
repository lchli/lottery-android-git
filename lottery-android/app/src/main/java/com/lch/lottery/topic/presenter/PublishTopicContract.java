package com.lch.lottery.topic.presenter;


import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.lch.lottery.ApiUrl;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.user.data.UserRepo;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.NetKit;
import com.lch.netkit.common.tool.JsonHelper;
import com.lch.netkit.string.Callback;
import com.lch.netkit.string.Parser;
import com.lch.netkit.string.StringRequestParams;

public interface PublishTopicContract {


    interface View {

        void onSuccess(TopicResponse data);

        void onFail(String msg);

    }

    interface Presenter {

        void registerView(View view);

        void unregisterView();

        void publishTopic(String title, String tag, String content, String topicID);
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
        public void publishTopic(@NonNull String title, String tag, @NonNull String content, String topicID) {
            UserResponse.User user = UserRepo.getLoginUser();
            if (user == null) {
                throw new IllegalStateException("未登录");
            }

            StringRequestParams params = new StringRequestParams()
                    .setUrl(ApiUrl.TOPIC)
                    .addParam("title", title)
                    .addParam("content", content)
                    .addParam("topicId", topicID==null?"":topicID)
                    .addParam("userId", user.userId)
                    .addParam("tag", tag==null?"":tag);

            NetKit.stringRequest().post(params, new Parser<TopicResponse>() {
                @Override
                public TopicResponse parse(String s) {
                    LogUtils.e(s);

                    TopicResponse response = JsonHelper.parseObject(s, TopicResponse.class);
                    if (response == null) {
                        throw new IllegalStateException("服务器数据解析失败");

                    }
                    if (response.status != ApiUrl.API_CODE_SUCCESS) {
                        throw new IllegalStateException(response.message);
                    }

                    return response;

                }
            }, new Callback<TopicResponse>() {
                @Override
                public void onSuccess(TopicResponse res) {

                    if (view != null) {
                        view.onSuccess(res);
                    }
                }

                @Override
                public void onFail(String s) {
                    if (view != null) {
                        view.onFail(s);
                    }
                }
            });


        }
    }

}
