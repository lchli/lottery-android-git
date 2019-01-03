package com.lch.lottery.topic.datainterface;

import android.support.annotation.NonNull;

import com.lch.lottery.topic.model.TopicResponse;
import com.lchli.arch.clean.ResponseValue;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface TopicRepo {

    class QueryParam {

        public SortField sort;
        public SortDirection sortDirect;
        public String tag;
        public String title;
        public String topicId;
        public String userId;
        public String page;
        public String pageSize;
    }

    enum SortDirection {
        ASC, DESC;
    }

    enum SortField {
        UPDATE_TIME;
    }


    @NonNull
    ResponseValue<List<TopicResponse.Topic>> getTopics(QueryParam param);

    @NonNull
    ResponseValue saveTopic(TopicResponse.Topic topic);


}
