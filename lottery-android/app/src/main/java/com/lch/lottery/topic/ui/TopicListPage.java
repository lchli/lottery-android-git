package com.lch.lottery.topic.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lch.lottery.R;
import com.lch.lottery.common.BottomSheetDialog;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.domain.SearchTopicCase;
import com.lch.lottery.topic.vm.TopicListVm;
import com.lch.lottery.util.EventBusUtils;
import com.lchli.utils.tool.VF;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListPage extends TabPage {


    private PullToRefreshListView topicListView;
    private TopicListAdapter mTopicListAdapter;
    private View createTopicFab;
    private View emptyView;
    private EditText etSearchKey;
    private TextView tvSearchBy;
    private TextView tvStartSearch;
    private TextView tvSorter;

    private final TopicListVm topicListVm = new TopicListVm();


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
        emptyView = VF.f(this, R.id.emptyView);
        etSearchKey = VF.f(this, R.id.etSearchKey);
        tvSearchBy = VF.f(this, R.id.tvSearchBy);
        tvStartSearch = VF.f(this, R.id.tvStartSearch);
        tvSorter = VF.f(this, R.id.tvSorter);

        topicListView.setMode(PullToRefreshBase.Mode.BOTH);
        tvSearchBy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchBy();
            }
        });
        tvStartSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        tvSorter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortBy();
            }
        });

        mTopicListAdapter = new TopicListAdapter();

        topicListView.setAdapter(mTopicListAdapter);
        createTopicFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteOrEditTopicActivity.launch(null, getContext());
            }
        });
        topicListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                topicListVm.onLoadMore();
            }
        });

        topicListVm.topicListDataSt.observeForever(new Observer<List<Object>>() {
            @Override
            public void onChanged(@Nullable List<Object> objects) {
                mTopicListAdapter.refresh(objects);
            }
        });
        topicListVm.emptyViewState.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    emptyView.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                }
            }
        });
        topicListVm.loadingViewState.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean != null) {
                    if (aBoolean) {
                        //do not need impl.
                    } else {
                        topicListView.onRefreshComplete();
                    }
                }
            }
        });
        topicListVm.loadMoreSt.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showShort(s);
            }
        });
        topicListVm.failSt.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showShort(s);
            }
        });
        topicListVm.searchByViewState.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvSearchBy.setText(s);
            }
        });
        topicListVm.sortByViewState.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvSorter.setText(s);
            }
        });

    }

    private void showSearchBy() {
        final BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        View root = View.inflate(getContext(), R.layout.topic_search_by, null);
        final TextView tvByTitle = VF.f(root, R.id.tvByTitle);
        final TextView tvByTag = VF.f(root, R.id.tvByTag);

        tvByTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topicListVm.setSearchBy(SearchTopicCase.SearchType.TITLE);
                mDialog.dismiss();
            }
        });
        tvByTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topicListVm.setSearchBy(SearchTopicCase.SearchType.TAG);
                mDialog.dismiss();
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
        final BottomSheetDialog mDialog = new BottomSheetDialog(getContext());
        View root = View.inflate(getContext(), R.layout.topic_sort_by, null);
        final TextView tvTimeAsc = VF.f(root, R.id.tvTimeAsc);
        final TextView tvTimeDesc = VF.f(root, R.id.tvTimeDesc);

        tvTimeAsc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                topicListVm.setSort(TopicRepo.SortField.UPDATE_TIME, TopicRepo.SortDirection.ASC);

                mDialog.dismiss();
            }
        });
        tvTimeDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                topicListVm.setSort(TopicRepo.SortField.UPDATE_TIME, TopicRepo.SortDirection.DESC);
                mDialog.dismiss();
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

        EventBusUtils.register(this);

        onRefresh();

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TopicListDataChangedEvent event) {
        onRefresh();
    }

    private void onRefresh() {
        topicListVm.onRefresh(etSearchKey.getText().toString());
    }

}
