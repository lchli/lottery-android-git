package com.lch.lottery.common;

import android.content.Context;
import android.widget.ImageView;

import com.lch.netkit.imageloader.LiImageLoader;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        LiImageLoader.instance().builder()
                .source(path.toString())
                .view(imageView)
                .display(context);
    }
}
