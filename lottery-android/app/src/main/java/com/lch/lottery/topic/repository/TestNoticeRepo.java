package com.lch.lottery.topic.repository;

import com.lch.lottery.topic.model.NoticeResponse;
import com.lch.netkit.string.ResponseValue;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/31.
 */

public class TestNoticeRepo implements NoticeRepo {

    @Override
    public ResponseValue<NoticeResponse> getNotice() {

        ResponseValue<NoticeResponse> res=new ResponseValue<>();

        NoticeResponse response=new NoticeResponse();
        response.data=new ArrayList<>();

        NoticeResponse.Notice notice=new NoticeResponse.Notice();
        notice.text="最新开奖：256期 开奖号123 试机号098";
        notice.linkUrl="1";
        response.data.add(notice);

        notice=new NoticeResponse.Notice();
        notice.text="最新开奖：257期 开奖号123 试机号098";
        notice.linkUrl="2";
        response.data.add(notice);

        res.data=response;

        return res;
    }
}
