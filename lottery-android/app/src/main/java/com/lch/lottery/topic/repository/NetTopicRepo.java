package com.lch.lottery.topic.repository;

import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.lch.lottery.DI;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.user.model.UserResponse;
import com.lch.lottery.user.repository.UserRepo;
import com.lch.lottery.util.ApiUrl;
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

public class NetTopicRepo implements TopicRepo {
    private UserRepo localUserRepo = DI.injectLocalUserRepo();

    @Override
    public ResponseValue<List<TopicResponse.Topic>> getTopics(String sort, String sortDirect,
                                                              String tag, String title,
                                                              String topicId, String userId, int page, int pageSize) {
        StringRequestParams params = new StringRequestParams()
                .addParam("sort", sort)
                .addParam("sortDirect", sortDirect)
                .addParam("tag", tag)
                .addParam("title", title)
                .addParam("topicId", topicId)
                .addParam("userId", userId)
                .addParam("page", page + "")
                .addParam("pageSize", pageSize + "")
                .setUrl(ApiUrl.TOPIC_GET);

        return NetKit.stringRequest().getSync(params, new Parser<List<TopicResponse.Topic>>() {
            @Override
            public List<TopicResponse.Topic> parse(String s) {
                LogUtils.e(s);

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
        ResponseValue<UserResponse.User> userRes = localUserRepo.getCurrentUser();
        if (userRes.data == null) {
            ResponseValue res = new ResponseValue();
            res.err = new NetworkError("未登录");
            return res;
        }

        StringRequestParams params = new StringRequestParams()
                .setUrl(ApiUrl.TOPIC_ADD)
                .addParam("title", topic.title)
                .addParam("content", topic.content)
                .addParam("topicId", topic.uid)
                .addParam("userId", userRes.data.userId)
                .addParam("tag", topic.tag);


        ResponseValue<TopicResponse> ret = NetKit.stringRequest().postSync(params, new Parser<TopicResponse>() {
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

        LogUtils.e(ret);

        return ret;

    }


}
