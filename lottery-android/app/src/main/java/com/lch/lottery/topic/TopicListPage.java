package com.lch.lottery.topic;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.lottery.topic.controller.TopicController;
import com.lch.netkit.common.tool.VF;
import com.lchli.pinedrecyclerlistview.library.pinnedListView.PinnedListView;

import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListPage extends TabPage {


    private final TopicController mPresenter = new TopicController();
    private PinnedListView topicListView;
    private TopicListAdapter mTopicListAdapter;
    private View createTopicFab;

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
    public void onCreateImpl() {
        if (isCreated) {
            return;
        }
        isCreated = true;

        mPresenter.setCallback(new TopicController.Callback() {
            @Override
            public void onGet(List<Object> data) {
                mTopicListAdapter.refresh(data);
            }

            @Override
            public void onFail(String msg) {
                ToastUtils.showLong(msg);
            }

        });
    }

    @Override
    public void onDestroyImpl() {
        if (isDestroyed) {
            return;
        }
        isDestroyed = true;

        mPresenter.setCallback(null);
    }

    @Override
    public void refresh() {
        super.refresh();
        mPresenter.getAllTopicsAndTag();
    }


}
