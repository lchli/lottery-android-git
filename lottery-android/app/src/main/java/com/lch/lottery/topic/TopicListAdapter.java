package com.lch.lottery.topic;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.VerticalScrollTextView;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.netkit.common.tool.VF;
import com.lch.netkit.imageloader.LiImageLoader;
import com.lchli.pinedrecyclerlistview.library.pinnedListView.PinnedListAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListAdapter extends PinnedListAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_NOTICE = 2;
    private static final int COUNT = 3;

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case TYPE_ITEM:
                return new ItemHolder(TYPE_ITEM, parent.getContext());

            case TYPE_NOTICE:
                return new NoticeHolder(TYPE_NOTICE, parent.getContext());

            case TYPE_ADD:
                return new AddHolder(TYPE_ADD, parent.getContext());

        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {

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

            case TYPE_ADD: {
                final AdResponse data = (AdResponse) getItem(position);
                final List<AdResponse.Ad> ads = data.data;

                AddHolder vh = (AddHolder) holder;
                vh.banner.setImages(ads);
                vh.banner.setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        AdResponse.Ad ad = (AdResponse.Ad) path;

                        LiImageLoader.instance().builder()
                                .source(ad.imgUrl)
                                .view(imageView)
                                .display(context);

                    }
                });
                vh.banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        AdResponse.Ad ad = ads.get(position);

                    }
                });
                vh.banner.start();
            }
            break;

            case TYPE_NOTICE: {
                final NoticeResponse data = (NoticeResponse) getItem(position);

                NoticeHolder vh = (NoticeHolder) holder;
                vh.pinName.setDataSource(data.data);
                vh.pinName.startPlay();
            }
            break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object obj = getItem(position);

        if (obj instanceof AdResponse) {
            return TYPE_ADD;
        }

        if (obj instanceof TopicResponse.Topic) {
            return TYPE_ITEM;
        }

        return TYPE_NOTICE;

    }


    @Override
    public int getViewTypeCount() {
        return COUNT;
    }

    private class NoticeHolder extends AbsViewHolder {

        private final View itemView;
        private final VerticalScrollTextView pinName;


        public NoticeHolder(int viewType, Context context) {
            super(viewType);
            itemView = View.inflate(context, R.layout.item_topic_pin, null);
            pinName = VF.f(itemView, R.id.pinName);
        }

        @Override
        protected View getItemView() {
            return itemView;
        }
    }

    private class AddHolder extends AbsViewHolder {

        private final View itemView;
        private final Banner banner;


        public AddHolder(int viewType, Context context) {
            super(viewType);
            itemView = View.inflate(context, R.layout.list_item_banner, null);
            banner = VF.f(itemView, R.id.banner);
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
