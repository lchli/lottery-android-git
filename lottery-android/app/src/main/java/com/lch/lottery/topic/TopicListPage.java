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
import android.widget.EditText;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.controller.TopicController;
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


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopics(false);
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
        mPresenter.getAllTopicsAndTag();
    }

}
