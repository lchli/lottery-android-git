package com.lch.lottery.topic.repository;

import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.netkit.string.ResponseValue;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface NoticeRepo {

    ResponseValue<NoticeResponse> getNotice();

}
