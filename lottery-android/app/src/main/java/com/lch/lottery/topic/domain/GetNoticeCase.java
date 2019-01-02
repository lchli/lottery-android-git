package com.lch.lottery.topic.domain;

import com.lch.lottery.topic.datainterface.NoticeRepo;
import com.lch.lottery.topic.model.NoticeResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class GetNoticeCase extends UseCase<Void, List<NoticeResponse.Notice>> {


    private NoticeRepo repo;

    public GetNoticeCase(NoticeRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ResponseValue<List<NoticeResponse.Notice>> execute(Void param) {

        return repo.getNotice();
    }
}
