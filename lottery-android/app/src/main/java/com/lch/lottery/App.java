package com.lch.lottery;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.blankj.utilcode.util.Utils;
import com.lch.netkit.NetKit;


public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        app = this;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });
        super.onCreate();

        Utils.init(this);
        NetKit.init();
    }

    public static void launchIt(Intent it) {
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(it);

    }

    public static void launchIt(Intent it, Activity context) {
        context.startActivity(it);
    }


    public static void launchActivity(Class<? extends Activity> cls) {

        launchIt(new Intent(app, cls));
    }

    public static void launchActivity(Class<? extends Activity> cls, Activity context) {

        launchIt(new Intent(app, cls), context);
    }
}
