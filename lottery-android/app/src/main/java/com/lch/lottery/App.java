package com.lch.lottery;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.lch.lottery.topic.TopicModuleFactory;
import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.netkit.v2.NetKit;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.imgloader.ImgLoaderManager;

import java.util.ArrayList;
import java.util.List;


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
        ImgLoaderManager.getINS().init(this, null);
        //SDKInitializer.initialize(this);

        TopicModuleInjector.getINS().initFactory(new TopicModuleFactory() {
            @Override
            public TopicRepo provideTopicRepo() {
                return new TopicRepo() {
                    @NonNull
                    @Override
                    public ResponseValue<List<TopicResponse.Topic>> getTopics(QueryParam param) {
                        return new ResponseValue<>();
                    }

                    @NonNull
                    @Override
                    public ResponseValue saveTopic(TopicResponse.Topic topic) {
                        return new ResponseValue<>();
                    }


                };
            }

            @Override
            public AdRepo provideAdRepo() {
                return new AdRepo() {
                    @Override
                    public ResponseValue<AdResponse> getAd() {
                        ResponseValue<AdResponse> test = new ResponseValue<>();
                        test.data = new AdResponse();
                        test.data.data = new ArrayList<>();

                        AdResponse.Ad ad = new AdResponse.Ad();
                        ad.imgUrl = "https://www.baidu.com/img/bd_logo1.png";
                        test.data.data.add(ad);

                        ad = new AdResponse.Ad();
                        ad.imgUrl = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3756930651,2591929300&fm=173&s=A4006DB54A23149C5F9981060300D0C1&w=218&h=146&img.JPEG";
                        test.data.data.add(ad);

                        return test;
                    }
                };
            }

            @Override
            public NoticeRepo provideNoticeRepo() {
                return new NoticeRepo() {
                    @Override
                    public ResponseValue<NoticeResponse> getNotice() {
                        ResponseValue<NoticeResponse> test = new ResponseValue<>();
                        test.data = new NoticeResponse();
                        test.data.data = new ArrayList<>();

                        NoticeResponse.Notice notice = new NoticeResponse.Notice();
                        notice.text = "最新开奖：256期 开奖号123 试机号098";
                        notice.linkUrl = "1";
                        test.data.data.add(notice);

                        notice = new NoticeResponse.Notice();
                        notice.text = "最新开奖：257期 开奖号123 试机号098";
                        notice.linkUrl = "2";
                        test.data.data.add(notice);

                        return test;
                    }
                };
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
