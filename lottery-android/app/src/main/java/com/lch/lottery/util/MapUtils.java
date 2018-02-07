package com.lch.lottery.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.apkfuns.logutils.LogUtils;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;

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

    public static boolean baiduMapNavigate(@NonNull NavPlace from, @NonNull NavPlace to, @NonNull Context context) {
        try {
            if (from.latLng == null || to.latLng == null) {
                return false;
            }
            Intent it = new Intent();
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String url = String.format(Locale.ENGLISH, "baidumap://map/direction?origin=name:%s|latlng:%f,%f&destination=name:%s|latlng:%f,%f",
                    from.name, from.latLng.latitude, from.latLng.longitude, to.name, to.latLng.latitude, to.latLng.longitude);

            LogUtils.e("nav map url:" + url);

            it.setData(Uri.parse(url));

            context.startActivity(it);

            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            ToastUtils.showShort("启动导航失败，请确保已安装地图应用");
            return false;
        }
    }


    public static boolean gaodeiMapNavigate(@NonNull NavPlace to, @NonNull Context context) {
        try {

            if (to.latLng == null) {
                return false;
            }

            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            String url = String.format(Locale.ENGLISH,
                    "androidamap://navi?sourceApplication=%s&poiname=%s&lat=%f&lon=%f&dev=1&style=2",
                    AppUtils.getAppName(), to.name, to.latLng.latitude, to.latLng.longitude);
            LogUtils.e("nav map url:" + url);

            Uri uri = Uri.parse(url);
            intent.setData(uri);

            context.startActivity(intent);

            return true;

        } catch (Throwable e) {
            e.printStackTrace();
            ToastUtils.showShort("启动导航失败，请确保已安装地图应用");
            return false;
        }

    }
}
