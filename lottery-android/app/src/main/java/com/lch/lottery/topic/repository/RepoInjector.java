package com.lch.lottery.topic.repository;

/**
 * Created by lichenghang on 2018/1/28.
 */

public final class RepoInjector {

    public static TopicRepo injectTopicRepo(){

        return new NetTopicRepo();
    }
}
