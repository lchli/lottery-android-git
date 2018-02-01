package com.lch.lottery.topic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.lch.netkit.common.base.BaseRecyclerAdapter;
import com.lch.netkit.common.tool.VF;
import com.lch.netkit.imageloader.LiImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListAdapter extends BaseRecyclerAdapter<Object> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_NOTICE = 2;
    private static final int COUNT = 3;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case TYPE_ITEM:
                itemView = View.inflate(parent.getContext(), R.layout.item_topic, null);

                return new ItemHolder(itemView);

            case TYPE_NOTICE:
                itemView = View.inflate(parent.getContext(), R.layout.item_topic_pin, null);
                return new NoticeHolder(itemView);

            case TYPE_ADD:
                itemView = View.inflate(parent.getContext(), R.layout.list_item_banner, null);
                return new AddHolder(itemView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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


    private class NoticeHolder extends RecyclerView.ViewHolder {

        private final VerticalScrollTextView pinName;


        public NoticeHolder(View itemView) {
            super(itemView);
            pinName = VF.f(itemView, R.id.pinName);
        }

    }

    private class AddHolder extends RecyclerView.ViewHolder {

        private final Banner banner;


        public AddHolder(View itemView) {
            super(itemView);
            banner = VF.f(itemView, R.id.banner);
        }


    }

    private class ItemHolder extends RecyclerView.ViewHolder {


        private final TextView topicTitleTextView;
        private final TextView authorNameTextView;
        private final TextView updateTimeTextView;

        public ItemHolder(View itemView) {
            super(itemView);

            topicTitleTextView = VF.f(itemView, R.id.topicTitleTextView);
            authorNameTextView = VF.f(itemView, R.id.authorNameTextView);
            updateTimeTextView = VF.f(itemView, R.id.updateTimeTextView);
        }

    }
}
