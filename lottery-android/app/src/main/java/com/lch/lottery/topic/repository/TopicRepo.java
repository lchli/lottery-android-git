package com.lch.lottery.topic.repository;

import com.lch.lottery.topic.model.TopicResponse;
import com.lch.netkit.string.ResponseValue;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface TopicRepo {


    ResponseValue<List<TopicResponse.Topic>> getAllTopics();

    ResponseValue addOrUpdateTopic(TopicResponse.Topic topic);

}
