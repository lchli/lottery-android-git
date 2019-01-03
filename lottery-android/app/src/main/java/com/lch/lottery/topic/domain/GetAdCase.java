package com.lch.lottery.topic.domain;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class GetAdCase extends UseCase<Void, AdResponse> {


    private AdRepo repo;

    public GetAdCase(AdRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ResponseValue<AdResponse> execute(Void parameters) {
        return repo.getAd();
    }
}
