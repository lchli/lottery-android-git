package com.lch.lottery.topic;

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

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lch.lottery.R;
import com.lch.lottery.common.BottomSheetDialog;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.controller.TopicController;
import com.lch.lottery.topic.model.SearchType;
import com.lch.lottery.topic.model.TopicSorter;
import com.lch.lottery.util.EventBusUtils;
import com.lch.netkit.common.tool.VF;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListPage extends TabPage {


    private final TopicController mPresenter = new TopicController();
    private PullToRefreshListView topicListView;
    private TopicListAdapter mTopicListAdapter;
    private View createTopicFab;
    private View emptyView;
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
        emptyView = VF.f(this, R.id.emptyView);
        etSearchKey = VF.f(this, R.id.etSearchKey);
        tvSearchBy = VF.f(this, R.id.tvSearchBy);
        tvStartSearch = VF.f(this, R.id.tvStartSearch);
        tvSorter = VF.f(this, R.id.tvSorter);

        topicListView.setMode(PullToRefreshBase.Mode.BOTH);
        tvSearchBy.setText(searchType.toString());
        tvSorter.setText(sorter.toString());

        tvSearchBy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchBy();
            }
        });
        tvStartSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                topicListView.setRefreshing();
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
                loadTopics();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadMore();
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
                searchType = SearchType.TITLE;
                tvSearchBy.setText(searchType.toString());
                mDialog.dismiss();
            }
        });
        tvByTag.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = SearchType.TAG;
                tvSearchBy.setText(searchType.toString());
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
                sorter = TopicSorter.TIME_ASC;
                tvSorter.setText(sorter.toString());
                mDialog.dismiss();

                topicListView.setRefreshing();
            }
        });
        tvTimeDesc.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sorter = TopicSorter.TIME_DESC;
                tvSorter.setText(sorter.toString());
                mDialog.dismiss();

                topicListView.setRefreshing();
            }
        });

        mDialog.contentView(root)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }

    private void onGetTopics(List<Object> data) {
        topicListView.onRefreshComplete();

        mTopicListAdapter.refresh(data);

        if (EmptyUtils.isEmpty(data)) {
            emptyView.setVisibility(VISIBLE);
        } else {
            emptyView.setVisibility(GONE);
        }
    }

    private void onFail(String msg) {
        topicListView.onRefreshComplete();
        ToastUtils.showLong(msg);
    }

    private void onNoMore() {
        topicListView.onRefreshComplete();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        EventBusUtils.register(this);

        topicListView.setRefreshing();
        loadTopics();
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        EventBusUtils.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TopicListDataChangedEvent event) {
        topicListView.setRefreshing();
    }


    private void loadTopics() {
        emptyView.setVisibility(GONE);

        switch (searchType) {
            case TITLE:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), null, etSearchKey.getText().toString(),
                        null, null, new Qcb(this));
                break;
            case TAG:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), etSearchKey.getText().toString(),
                        null, null, null, new Qcb(this));
                break;
            case ALL:
                mPresenter.getTopics(sorter.sortField(), sorter.sortDirec(), null, null,
                        null, null, new Qcb(this));
                break;
        }

    }


    private void loadMore() {
        switch (searchType) {
            case TITLE:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), null,
                        etSearchKey.getText().toString(), null, null, new Qcb(this));
                break;
            case TAG:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), etSearchKey.getText().toString(),
                        null, null, null, new Qcb(this));
                break;
            case ALL:
                mPresenter.loadMore(sorter.sortField(), sorter.sortDirec(), null, null,
                        null, null, new Qcb(this));
                break;
        }
    }


    private static class Qcb implements TopicController.Q {

        private WeakReference<TopicListPage> ref;

        private Qcb(TopicListPage ui) {
            ref = new WeakReference<>(ui);
        }

        @Override
        public void onGet(List<Object> data) {
            TopicListPage ui = ref.get();
            if (ui == null) {
                return;
            }
            ui.onGetTopics(data);

        }

        @Override
        public void onFail(String msg) {
            TopicListPage ui = ref.get();
            if (ui == null) {
                return;
            }
            ui.onFail(msg);
        }

        @Override
        public void onNoMore() {
            TopicListPage ui = ref.get();
            if (ui == null) {
                return;
            }
            ui.onNoMore();

        }
    }
}
