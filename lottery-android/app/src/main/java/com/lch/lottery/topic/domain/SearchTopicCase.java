package com.lch.lottery.topic.domain;

import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.model.TopicResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class SearchTopicCase extends UseCase<SearchTopicCase.Param, List<TopicResponse.Topic>> {

    public static class Param {
        public SearchType searchBy;
        public TopicRepo.SortField sortBy;
        public TopicRepo.SortDirection sortDirect;
        public String searchKey;
        public int page;
        public int pageSize;
    }


    public enum SearchType {
        ALL,
        TITLE,
        TAG;
    }


    private TopicRepo topicRepo;

    public SearchTopicCase(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    @Override
    protected ResponseValue<List<TopicResponse.Topic>> execute(Param param) {
        TopicRepo.QueryParam queryParam = new TopicRepo.QueryParam();

        if (param.searchBy == SearchType.TITLE) {
            queryParam.title = param.searchKey;
        } else if (param.searchBy == SearchType.TAG) {
            queryParam.tag = param.searchKey;
        }

        queryParam.sort = param.sortBy;
        queryParam.sortDirect = param.sortDirect;


        return topicRepo.getTopics(queryParam);
    }
}
