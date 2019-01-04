package com.lch.lottery.topic.ui;

import android.app.Activity;
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
import com.lch.lottery.R;
import com.lch.lottery.common.BottomSheetDialog;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.domain.SearchTopicCase;
import com.lch.lottery.topic.presenter.TopicListPresenter;
import com.lch.lottery.util.EventBusUtils;
import com.lchli.utils.tool.VF;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListPage extends TabPage implements TopicListPresenter.MvpView {


    private ListView topicListView;
    private SmartRefreshLayout refreshLayout;
    private TopicListAdapter mTopicListAdapter;
    private View createTopicFab;
    private View emptyView;
    private EditText etSearchKey;
    private TextView tvSearchBy;
    private TextView tvStartSearch;
    private TextView tvSorter;

    private TopicListPresenter topicListVm;


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
        topicListVm = new TopicListPresenter(new ViewProxy(this));

        View.inflate(getContext(), R.layout.page_topic_list, this);
        topicListView = VF.f(this, R.id.topicListView);
        refreshLayout = VF.f(this, R.id.refreshLayout);
        createTopicFab = VF.f(this, R.id.createTopicFab);
        emptyView = VF.f(this, R.id.emptyView);
        etSearchKey = VF.f(this, R.id.etSearchKey);
        tvSearchBy = VF.f(this, R.id.tvSearchBy);
        tvStartSearch = VF.f(this, R.id.tvStartSearch);
        tvSorter = VF.f(this, R.id.tvSorter);

        tvSearchBy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchBy();
            }
        });
        tvStartSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshImpl();
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
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                topicListVm.onLoadMore();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshImpl();
            }
        });
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

    }

    @Override
    public void showListData(List<Object> datas) {
        mTopicListAdapter.refresh(datas);
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showShort(msg);

    }

    @Override
    public void showNoMore(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            //do nothing.
        } else {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showEmpty(boolean show) {
        emptyView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showSortText(String text) {
        tvSorter.setText(text);
    }

    @Override
    public void showSearchByText(String text) {
        tvSearchBy.setText(text);
    }

    private static class ViewProxy implements TopicListPresenter.MvpView {

        private final WeakReference<TopicListPresenter.MvpView> ref;

        private ViewProxy(TopicListPresenter.MvpView activity) {
            this.ref = new WeakReference<>(activity);
        }


        @Override
        public void showListData(List<Object> datas) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showListData(datas);
            }

        }

        @Override
        public void showFail(String msg) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showFail(msg);
            }
        }

        @Override
        public void showNoMore(String msg) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showNoMore(msg);
            }

        }

        @Override
        public void showLoading(boolean show) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showLoading(show);
            }

        }

        @Override
        public void showEmpty(boolean show) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showEmpty(show);
            }

        }

        @Override
        public void showSortText(String text) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showSortText(text);
            }

        }

        @Override
        public void showSearchByText(String text) {
            TopicListPresenter.MvpView ui = ref.get();
            if (ui != null) {
                ui.showSearchByText(text);
            }

        }
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

        onRefreshImpl();

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TopicListDataChangedEvent event) {
        onRefreshImpl();
    }

    private void onRefreshImpl() {
        topicListVm.onRefresh(etSearchKey.getText().toString());
    }

}
