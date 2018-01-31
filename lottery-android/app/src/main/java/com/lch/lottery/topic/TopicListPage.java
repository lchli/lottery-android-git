package com.lch.lottery.topic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.BottomSheetDialog;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.controller.TopicController;
import com.lch.lottery.topic.model.SearchType;
import com.lch.lottery.topic.model.TopicSorter;
import com.lch.lottery.util.EventBusUtils;
import com.lch.netkit.common.tool.VF;
import com.lchli.pinedrecyclerlistview.library.pinnedListView.PinnedListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListPage extends TabPage {


    private final TopicController mPresenter = new TopicController();
    private PinnedListView topicListView;
    private TopicListAdapter mTopicListAdapter;
    private View createTopicFab;
    private View emptyView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText etSearchKey;
    private TextView tvSearchBy;
    private TextView tvStartSearch;
    private TextView tvSorter;

    private SearchType searchType = SearchType.ALL;
    private TopicSorter sorter = TopicSorter.TIME_ASC;


    public TopicListPage(@NonNull Context context) {
        super(context);
        init();
    }

    public TopicListPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopicListPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        super.init();
        View.inflate(getContext(), R.layout.page_topic_list, this);
        topicListView = VF.f(this, R.id.topicListView);
        createTopicFab = VF.f(this, R.id.createTopicFab);
        swipeRefreshLayout = VF.f(this, R.id.swipeRefreshLayout);
        emptyView = VF.f(this, R.id.emptyView);
        etSearchKey = VF.f(this, R.id.etSearchKey);
        tvSearchBy = VF.f(this, R.id.tvSearchBy);
        tvStartSearch = VF.f(this, R.id.tvStartSearch);
        tvSorter = VF.f(this, R.id.tvSorter);

        tvSearchBy.setText(searchType.toString());
        tvSorter.setText(sorter.toString());

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopics(false);
            }
        });
        tvSearchBy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchBy();
            }
        });
        tvStartSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopics(true);
            }
        });
        tvSorter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortBy();
            }
        });

        mTopicListAdapter = new TopicListAdapter();

        topicListView.setPinnedAdapter(mTopicListAdapter);
        createTopicFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteOrEditTopicActivity.launch(null, getContext());
            }
        });

        topicListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(!view.canScrollVertically(1)){
                    loadMore();
                }

            }
        });

    }

    private void showSearchBy() {
        BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        View root = View.inflate(getContext(), R.layout.topic_search_by, null);
        final TextView tvByTitle = VF.f(root, R.id.tvByTitle);
        final TextView tvByTag = VF.f(root, R.id.tvByTag);

        tvByTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = SearchType.TITLE;
                tvSearchBy.setText(searchType.toString());
            }
        });
        tvByTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = SearchType.TAG;
                tvSearchBy.setText(searchType.toString());
            }
        });

        mDialog.contentView(root)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }


    private void showSortBy() {
        BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        View root = View.inflate(getContext(), R.layout.topic_sort_by, null);
        final TextView tvTimeAsc = VF.f(root, R.id.tvTimeAsc);
        final TextView tvTimeDesc = VF.f(root, R.id.tvTimeDesc);

        tvTimeAsc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sorter = TopicSorter.TIME_ASC;
                tvSorter.setText(sorter.toString());
            }
        });
        tvTimeDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sorter = TopicSorter.TIME_DESC;
                tvSorter.setText(sorter.toString());
            }
        });

        mDialog.contentView(root)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mPresenter.setCallback(new TopicController.Callback() {
            @Override
            public void onGet(List<Object> data) {

                swipeRefreshLayout.setRefreshing(false);
                mTopicListAdapter.refresh(data);

                if (EmptyUtils.isEmpty(data)) {
                    emptyView.setVisibility(VISIBLE);
                } else {
                    emptyView.setVisibility(GONE);
                }
            }

            @Override
            public void onFail(String msg) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showLong(msg);
            }

        });
        EventBusUtils.register(this);

        loadTopics(true);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mPresenter.setCallback(null);
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TopicListDataChangedEvent event) {
        loadTopics(true);
    }


    private void loadTopics(boolean setRefreshing) {
        emptyView.setVisibility(GONE);
        swipeRefreshLayout.setRefreshing(setRefreshing);

        switch (searchType) {
            case TITLE:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), null, etSearchKey.getText().toString(), null, null);
                break;
            case TAG:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), etSearchKey.getText().toString(), null, null, null);
                break;
            case ALL:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), null, null, null, null);
                break;
        }

    }


    private void loadMore() {
        switch (searchType) {
            case TITLE:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), null, etSearchKey.getText().toString(), null, null);
                break;
            case TAG:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), etSearchKey.getText().toString(), null, null, null);
                break;
            case ALL:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), null, null, null, null);
                break;
        }
    }

}
