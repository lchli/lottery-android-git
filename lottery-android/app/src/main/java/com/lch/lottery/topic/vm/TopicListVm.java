package com.lch.lottery.topic.vm;

import android.arch.lifecycle.MutableLiveData;

import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.domain.GetAdCase;
import com.lch.lottery.topic.domain.SearchTopicCase;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.SearchType;
import com.lch.lottery.topic.model.TopicResponse;
import com.lchli.arch.clean.ResponseValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class TopicListVm {

    private SearchTopicCase searchTopicCase = new SearchTopicCase(TopicModuleInjector.getINS().provideTopicRepo());
    private GetAdCase getAdCase = new GetAdCase(TopicModuleInjector.getINS().provideAdRepo());


    public MutableLiveData<List<Object>> topicListDataVm = new MutableLiveData<>();
    public MutableLiveData<String> failVm = new MutableLiveData<>();
    public MutableLiveData<String> loadMoreVm = new MutableLiveData<>();

    private SearchTopicCase.SortBy sortBy = SearchTopicCase.SortBy.TIME;
    private SearchTopicCase.SortDirection sortDirection = SearchTopicCase.SortDirection.ASC;
    private SearchType searchType = SearchType.ALL;
    private String searchKey = "";

    private List<Object> currentDatas = new ArrayList<>();
    private int page = 0;
    private static final int PAGE_SIZE = 20;
    private boolean haveMore = true;


    public void sort(SearchTopicCase.SortBy sortBy, SearchTopicCase.SortDirection sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;

    }

    public void setSearchBy(SearchType searchBy) {
        this.searchType = searchType;
    }

    public void onRefresh() {
        currentDatas.clear();
        page = 0;
        haveMore = true;

        final SearchTopicCase.Param param = new SearchTopicCase.Param();
        param.sortBy = sortBy;
        param.sortDirect = sortDirection;
        param.searchBy = searchType;
        param.searchKey = searchKey;
        param.page = page;
        param.pageSize = PAGE_SIZE;

        getAdCase.getTaskExecutor().executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                ResponseValue<List<TopicResponse.Topic>> topicRes = searchTopicCase.invokeSync(param);
                if (topicRes.hasError()) {
                    failVm.postValue(topicRes.getErrorMsg());
                    return;
                }

                if (topicRes.data != null && !topicRes.data.isEmpty()) {
                    datas.addAll(topicRes.data);
                    page++;
                } else {
                    haveMore = false;
                }

                ResponseValue<List<AdResponse.Ad>> adRes = getAdCase.invokeSync(null);
                if (!adRes.hasError() && adRes.data != null) {
                    datas.addAll(0, adRes.data);
                }

                currentDatas.addAll(datas);

                topicListDataVm.postValue(datas);

            }
        });


    }


    public void onLoadMore() {
        if (!haveMore) {
            loadMoreVm.postValue("no more.");
            return;
        }

        final SearchTopicCase.Param param = new SearchTopicCase.Param();
        param.sortBy = sortBy;
        param.sortDirect = sortDirection;
        param.searchBy = searchType;
        param.searchKey = searchKey;
        param.page = page;
        param.pageSize = PAGE_SIZE;

        getAdCase.getTaskExecutor().executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                ResponseValue<List<TopicResponse.Topic>> topicRes = searchTopicCase.invokeSync(param);
                if (topicRes.hasError()) {
                    failVm.postValue(topicRes.getErrorMsg());
                    return;
                }

                if (topicRes.data != null && !topicRes.data.isEmpty()) {
                    currentDatas.addAll(topicRes.data);
                    page++;
                } else {
                    haveMore = false;
                }


                datas.addAll(currentDatas);

                topicListDataVm.postValue(datas);

            }
        });

    }

}
