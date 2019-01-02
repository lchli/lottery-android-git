package com.lch.lottery.topic;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.datainterface.TopicRepo;

/**
 * Created by lichenghang on 2019/1/1.
 */

public interface TopicModuleFactory {

    TopicRepo provideTopicRepo();

    AdRepo provideAdRepo();

    NoticeRepo provideNoticeRepo();
}
