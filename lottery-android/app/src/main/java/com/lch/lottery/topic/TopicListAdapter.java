package com.lch.lottery.topic;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.lch.lottery.R;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.netkit.common.tool.VF;
import com.lchli.pinedrecyclerlistview.library.ListSectionData;
import com.lchli.pinedrecyclerlistview.library.pinnedListView.PinnedListAdapter;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListAdapter extends PinnedListAdapter {

    public static final int TYPE_PIN = 0;
    public static final int TYPE_ITEM = 1;

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_PIN:
                return new PinHolder(TYPE_PIN, parent.getContext());

            case TYPE_ITEM:
                return new ItemHolder(TYPE_ITEM, parent.getContext());

        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_PIN: {
                TopicSection data = (TopicSection) getItem(position);
                PinHolder vh = (PinHolder) holder;
                if (TextUtils.isEmpty(data.tag)) {
                    vh.pinName.setText("默认");
                } else {
                    vh.pinName.setText(data.tag);
                }
            }
            break;
            case TYPE_ITEM: {
                final TopicResponse.Topic data = (TopicResponse.Topic) getItem(position);
                ItemHolder vh = (ItemHolder) holder;
                vh.topicTitleTextView.setText(data.title);
                vh.authorNameTextView.setText(data.userName);
                vh.updateTimeTextView.setText(TimeUtils.millis2String(data.updateTime));
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TopicDetailActivity.launch(data, v.getContext());
                    }
                });
            }
            break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object o = getItem(position);
        if (o instanceof TopicResponse.Topic) {
            return TYPE_ITEM;
        } else if (o instanceof ListSectionData) {
            return ((ListSectionData) o).sectionViewType;
        }
        return super.getItemViewType(position);
    }

    private class PinHolder extends AbsViewHolder {

        private final View itemView;
        private final TextView pinName;


        public PinHolder(int viewType, Context context) {
            super(viewType);
            itemView = View.inflate(context, R.layout.item_topic_pin, null);
            pinName = VF.f(itemView, R.id.pinName);
        }

        @Override
        protected View getItemView() {
            return itemView;
        }
    }

    private class ItemHolder extends AbsViewHolder {

        private final View itemView;
        private final TextView topicTitleTextView;
        private final TextView authorNameTextView;
        private final TextView updateTimeTextView;

        public ItemHolder(int viewType, Context context) {
            super(viewType);
            itemView = View.inflate(context, R.layout.item_topic, null);
            topicTitleTextView = VF.f(itemView, R.id.topicTitleTextView);
            authorNameTextView = VF.f(itemView, R.id.authorNameTextView);
            updateTimeTextView = VF.f(itemView, R.id.updateTimeTextView);
        }

        @Override
        protected View getItemView() {
            return itemView;
        }
    }
}
