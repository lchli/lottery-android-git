package com.lch.lottery.topic;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.datainterface.TopicRepo;

/**
 * Created by lichenghang on 2019/1/1.
 */

public final class TopicModuleInjector implements TopicModuleFactory {

    private static final TopicModuleInjector INS=new TopicModuleInjector();

    public static TopicModuleInjector getINS() {
        return INS;
    }

    private TopicModuleFactory impl;


    public void initFactory(TopicModuleFactory factory) {
        impl = factory;
    }


    @Override
    public TopicRepo provideTopicRepo() {
        return impl.provideTopicRepo();
    }

    @Override
    public AdRepo provideAdRepo() {
        return impl.provideAdRepo();
    }

    @Override
    public NoticeRepo provideNoticeRepo() {
        return impl.provideNoticeRepo();
    }
}
