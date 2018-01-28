package com.lch.lottery.topic.repository;

import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.lch.lottery.ApiUrl;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.user.data.UserRepo;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.NetKit;
import com.lch.netkit.common.tool.JsonHelper;
import com.lch.netkit.file.helper.NetworkError;
import com.lch.netkit.string.Parser;
import com.lch.netkit.string.ResponseValue;
import com.lch.netkit.string.StringRequestParams;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

class NetTopicRepo implements TopicRepo {

    @Override
    public ResponseValue<List<TopicResponse.Topic>> getAllTopics() {
        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.TOPIC);

        return NetKit.stringRequest().getSync(params, new Parser<List<TopicResponse.Topic>>() {
            @Override
            public List<TopicResponse.Topic> parse(String s) {
                TopicResponse response = JsonHelper.parseObject(s, TopicResponse.class);
                if (response == null) {
                    throw new IllegalStateException("服务器数据解析失败");

                }
                if (response.status != ApiUrl.API_CODE_SUCCESS) {
                    throw new IllegalStateException(response.message);
                }

                return response.data;

            }
        });

    }

    @Override
    public ResponseValue addOrUpdateTopic(@NonNull TopicResponse.Topic topic) {
        UserResponse.User user = UserRepo.getLoginUser();
        if (user == null) {
            ResponseValue res = new ResponseValue();
            res.err = new NetworkError("未登录");
            return res;
        }

        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.TOPIC)
                .addParam("title", topic.title == null ? "" : topic.title)
                .addParam("content", topic.content == null ? "" : topic.content)
                .addParam("topicId", topic.uid == null ? "" : topic.uid)
                .addParam("userId", user.userId == null ? "" : user.userId)
                .addParam("tag", topic.tag == null ? "" : topic.tag);


        return NetKit.stringRequest().postSync(params, new Parser<TopicResponse>() {
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
        });

    }


}
