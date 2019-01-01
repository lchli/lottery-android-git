package com.lch.lottery;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.Utils;
import com.lch.lottery.topic.TopicModuleFactory;
import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.netkit.v2.NetKit;
import com.lchli.imgloader.ImgLoaderManager;


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
        NetKit.init(this);
        ImgLoaderManager.getINS().init(this,null);
        SDKInitializer.initialize(this);

        TopicModuleInjector.getINS().initFactory(new TopicModuleFactory() {
            @Override
            public TopicRepo provideTopicRepo() {
                return null;
            }
        });

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
