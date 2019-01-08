package com.lch.lottery.topic.presenter;


import android.support.annotation.NonNull;

import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.domain.GetAdCase;
import com.lch.lottery.topic.domain.GetNoticeCase;
import com.lch.lottery.topic.domain.SearchTopicCase;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.lottery.topic.model.TopicResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lichenghang on 2019/1/1.
 */

public class TopicListPresenter {

    public interface MvpView {
        void showListData(List<Object> datas);

        void showFail(String msg);

        void showNoMore(String msg);

        void showLoading(boolean show);

        void showEmpty(boolean show);

        void showSortText(String text);

        void showSearchByText(String text);

    }


    private SearchTopicCase searchTopicCase = new SearchTopicCase(TopicModuleInjector.getINS().provideTopicRepo());
    private GetAdCase getAdCase = new GetAdCase(TopicModuleInjector.getINS().provideAdRepo());
    private GetNoticeCase getNoticeCase = new GetNoticeCase(TopicModuleInjector.getINS().provideNoticeRepo());


    private TopicRepo.SortField sortBy = TopicRepo.SortField.UPDATE_TIME;
    private TopicRepo.SortDirection sortDirection = TopicRepo.SortDirection.ASC;
    private SearchTopicCase.SearchType searchType = SearchTopicCase.SearchType.ALL;
    private String searchKey = "";

    private List<Object> currentDatas = new ArrayList<>();
    private int page = 0;
    private static final int PAGE_SIZE = 20;
    private boolean haveMore = true;
    private MvpView view;

    public TopicListPresenter(@NonNull MvpView view) {
        this.view = view;
    }

    public void setSort(TopicRepo.SortField sortBy, TopicRepo.SortDirection sortDirection) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        onRefresh(searchKey);
    }

    public void setSearchBy(SearchTopicCase.SearchType searchType) {
        this.searchType = searchType;
        onRefresh(searchKey);
    }

    private String formatSortText() {
        StringBuilder ret = new StringBuilder();

        if (sortBy == TopicRepo.SortField.UPDATE_TIME) {
            ret.append("时间");
        }

        if (sortDirection == TopicRepo.SortDirection.ASC) {
            ret.append("升序");
        } else {
            ret.append("降序");
        }

        return ret.toString();
    }

    private String formatSearchByText() {
        switch (searchType) {
            case ALL:
                return "全部";
            case TAG:
                return "标签";
            case TITLE:
                return "标题";
            default:
                return "";
        }

    }

    public void onRefresh(String key) {
        searchKey = key;
        currentDatas.clear();
        page = 0;
        haveMore = true;
        view.showEmpty(false);
        view.showSortText(formatSortText());
        view.showSearchByText(formatSearchByText());
        view.showLoading(true);


        final SearchTopicCase.Param param = new SearchTopicCase.Param();
        param.sortBy = sortBy;
        param.sortDirect = sortDirection;
        param.searchBy = searchType;
        param.searchKey = searchKey;
        param.page = page;
        param.pageSize = PAGE_SIZE;

        UseCase.executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                final ResponseValue<List<TopicResponse.Topic>> topicRes = searchTopicCase.invokeSync(param);
                if (topicRes.hasError()) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showFail(topicRes.getErrorMsg());
                            view.showLoading(false);
                        }
                    });

                    return;
                }

                if (topicRes.data != null && !topicRes.data.isEmpty()) {
                    datas.addAll(topicRes.data);
                    page++;
                } else {
                    haveMore = false;
                }

                ResponseValue<NoticeResponse> noticeRes = getNoticeCase.invokeSync(null);
                if (!noticeRes.hasError() && noticeRes.data != null) {
                    datas.add(0, noticeRes.data);
                }

                ResponseValue<AdResponse> adRes = getAdCase.invokeSync(null);
                if (!adRes.hasError() && adRes.data != null) {
                    datas.add(0, adRes.data);
                }

                currentDatas.addAll(datas);

                UseCase.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showListData(datas);
                        if (datas.isEmpty()) {
                            view.showEmpty(true);
                        }

                        view.showLoading(false);
                    }
                });


            }
        });


    }


    public void onLoadMore() {
        view.showLoading(true);

        if (!haveMore) {
            view.showNoMore("已无更多数据！");
            view.showLoading(false);
            return;
        }

        final SearchTopicCase.Param param = new SearchTopicCase.Param();
        param.sortBy = sortBy;
        param.sortDirect = sortDirection;
        param.searchBy = searchType;
        param.searchKey = searchKey;
        param.page = page;
        param.pageSize = PAGE_SIZE;

        UseCase.executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final List<Object> datas = new ArrayList<>();

                final ResponseValue<List<TopicResponse.Topic>> topicRes = searchTopicCase.invokeSync(param);
                if (topicRes.hasError()) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            view.showFail(topicRes.getErrorMsg());
                            view.showLoading(false);
                        }
                    });

                    return;
                }

                if (topicRes.data != null && !topicRes.data.isEmpty()) {
                    currentDatas.addAll(topicRes.data);
                    page++;
                } else {
                    haveMore = false;
                }


                datas.addAll(currentDatas);

                UseCase.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        view.showListData(datas);
                        view.showLoading(false);
                    }
                });


            }
        });

    }

}
