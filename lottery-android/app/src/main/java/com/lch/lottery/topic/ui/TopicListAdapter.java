package com.lch.lottery.topic.ui;

import android.content.Context;
import android.net.Uri;
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
import com.lchli.imgloader.ImgLoaderManager;
import com.lchli.imgloader.ImgSource;
import com.lchli.utils.base.AbsAdapter;
import com.lchli.utils.tool.VF;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicListAdapter extends AbsAdapter<Object> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private static final int TYPE_NOTICE = 2;
    private static final int COUNT = 3;


    @Override
    public AbsAdapter.AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case TYPE_ITEM:
                itemView = View.inflate(parent.getContext(), R.layout.item_topic, null);

                return new ItemHolder(itemView, viewType);

            case TYPE_NOTICE:
                itemView = View.inflate(parent.getContext(), R.layout.item_topic_pin, null);
                return new NoticeHolder(itemView, viewType);

            case TYPE_ADD:
                itemView = View.inflate(parent.getContext(), R.layout.list_item_banner, null);
                return new AddHolder(itemView, viewType);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbsAdapter.AbsViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {

            case TYPE_ITEM: {
                final TopicResponse.Topic data = (TopicResponse.Topic) getItem(position);
                ItemHolder vh = (ItemHolder) holder;
                String title = String.format("(%s)%s", data.tag, data.title);
                vh.topicTitleTextView.setText(title);
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

                        ImgLoaderManager.getINS().display(imageView, ImgSource.create().setImgUri(Uri.parse(ad.imgUrl)), null);

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

    private class NoticeHolder extends AbsAdapter.AbsViewHolder {

        private final VerticalScrollTextView pinName;
        private View itemView;


        public NoticeHolder(View itemView, int viewtype) {
            super(viewtype);
            this.itemView = itemView;
            pinName = VF.f(itemView, R.id.pinName);
        }

        @Override
        protected View getItemView() {
            return itemView;
        }
    }

    private class AddHolder extends AbsAdapter.AbsViewHolder {

        private final Banner banner;
        private View itemView;


        public AddHolder(View itemView, int viewtype) {
            super(viewtype);
            this.itemView = itemView;
            banner = VF.f(itemView, R.id.banner);

        }

        @Override
        protected View getItemView() {
            return itemView;
        }
    }

    private class ItemHolder extends AbsAdapter.AbsViewHolder {


        private final TextView topicTitleTextView;
        private final TextView authorNameTextView;
        private final TextView updateTimeTextView;
        private View itemView;

        public ItemHolder(View itemView, int viewtype) {
            super(viewtype);
            this.itemView = itemView;

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
