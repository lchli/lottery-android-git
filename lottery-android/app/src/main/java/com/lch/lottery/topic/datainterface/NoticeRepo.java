package com.lch.lottery.topic.datainterface;

import com.lch.lottery.topic.model.NoticeResponse;
import com.lchli.arch.clean.ResponseValue;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface NoticeRepo {

    ResponseValue<NoticeResponse> getNotice();

}
