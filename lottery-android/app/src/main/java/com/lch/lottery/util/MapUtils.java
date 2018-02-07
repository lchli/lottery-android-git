package com.lch.lottery.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.baidu.mapapi.model.LatLng;

import java.util.Locale;

/**
 * Created by Administrator on 2018/2/7.
 */

public final class MapUtils {

    public static class NavPlace {
        public String name;
        public LatLng latLng;

        public NavPlace name(String name) {
            this.name = name;
            return this;
        }

        public NavPlace latLng(LatLng latLng) {
            this.latLng = latLng;
            return this;
        }
    }

    public static void baiduMapNavigate(@NonNull NavPlace from, @NonNull NavPlace to, @NonNull Context context) {
        if (from.latLng == null || to.latLng == null) {
            return;
        }
        Intent it = new Intent();
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String url = String.format(Locale.ENGLISH, "baidumap://map/direction?origin=name:%s|latlng:%f,%f&destination=name:%s|latlng:%f,%f",
                from.name, from.latLng.latitude, from.latLng.longitude, to.name, to.latLng.latitude, to.latLng.longitude);

        LogUtils.e("nav map url:"+url);

        it.setData(Uri.parse(url));

        context.startActivity(it);
    }
}
