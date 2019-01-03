package com.lch.lottery.topic.domain;

import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class GetNoticeCase extends UseCase<Void, NoticeResponse> {


    private NoticeRepo repo;

    public GetNoticeCase(NoticeRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ResponseValue<NoticeResponse> execute(Void param) {

        return repo.getNotice();
    }
}
