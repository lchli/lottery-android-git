package com.lch.lottery.topic.dataimpl;

import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lchli.arch.clean.ResponseValue;

import java.util.List;

/**
 * Created by Administrator on 2019/1/2.
 */

public class NetNoticeRepo implements NoticeRepo {
    @Override
    public ResponseValue<List<NoticeResponse.Notice>> getNotice() {
        return null;
    }
}
