package com.lch.lottery.topic.dataimpl;

import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.util.ApiUrl;
import com.lch.netkit.v2.NetKit;
import com.lch.netkit.v2.apirequest.ApiRequestParams;
import com.lch.netkit.v2.common.NetworkResponse;
import com.lch.netkit.v2.parser.Parser;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.utils.tool.JsonHelper;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

public class NetTopicRepo implements TopicRepo {

    @NonNull
    @Override
    public ResponseValue<List<TopicResponse.Topic>> getTopics(QueryParam param) {
        String sort = "updateTime";
        String sortDirect = "asc";

        if (param.sort == SortField.UPDATE_TIME) {
            sort = "updateTime";
        }

        if (param.sortDirect == SortDirection.ASC) {
            sortDirect = "asc";
        } else if (param.sortDirect == SortDirection.DESC) {
            sortDirect = "desc";
        }

        ApiRequestParams params = new ApiRequestParams()
                .addParam("sort", sort)
                .addParam("sortDirect", sortDirect)
                .addParam("tag", param.tag)
                .addParam("title", param.title)
                .addParam("topicId", param.topicId)
                .addParam("userId", param.userId)
                .addParam("page", param.page + "")
                .addParam("pageSize", param.pageSize + "")
                .setUrl(ApiUrl.TOPIC_GET);

        ResponseValue<List<TopicResponse.Topic>> ret = new ResponseValue<>();

        NetworkResponse<List<TopicResponse.Topic>> res = NetKit.apiRequest().syncGet(params, new Parser<List<TopicResponse.Topic>>() {
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

        if (res.hasError()) {
            ret.setErrorMsg(res.getErrorMsg());
            ret.code = res.httpCode;
            return ret;
        }

        ret.data = res.data;

        return ret;

    }

    @NonNull
    @Override
    public ResponseValue saveTopic(TopicResponse.Topic topic) {
        ApiRequestParams params = new ApiRequestParams()
                .setUrl(ApiUrl.TOPIC_ADD)
                .addParam("title", topic.title)
                .addParam("content", topic.content)
                .addParam("topicId", topic.uid)
                .addParam("userId", topic.userId)
                .addParam("tag", topic.tag);

        ResponseValue<List<TopicResponse.Topic>> ret = new ResponseValue<>();

        NetworkResponse<TopicResponse> res = NetKit.apiRequest().syncPost(params, new Parser<TopicResponse>() {
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

        if (res.hasError()) {
            ret.setErrorMsg(res.getErrorMsg());
            ret.code = res.httpCode;
            return ret;
        }

        return ret;
    }




}
