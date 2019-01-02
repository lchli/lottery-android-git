package com.lch.lottery;

import com.lch.lottery.topic.dataimpl.NetTopicRepo;
import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.user.repository.LocalUserRepo;
import com.lch.lottery.user.repository.NetUserRepo;
import com.lch.lottery.user.repository.UserRepo;

/**
 * Created by lichenghang on 2018/1/28.
 */

public final class DI {

    public static TopicRepo injectTopicRepo() {

        return new NetTopicRepo();
    }

    public static UserRepo injectLocalUserRepo() {
        return new LocalUserRepo();
    }

    public static UserRepo injectNetUserRepo() {
        return new NetUserRepo();
    }


}
