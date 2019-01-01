package com.lch.lottery.topic.domain;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class GetAdCase extends UseCase<Void, List<AdResponse.Ad>> {


    private AdRepo repo;

    public GetAdCase(AdRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ResponseValue<List<AdResponse.Ad>> execute(Void param) {

        return repo.getAd();
    }
}
