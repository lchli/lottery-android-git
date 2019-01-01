package com.lch.lottery.topic.dataimpl;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lchli.arch.clean.ResponseValue;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class NetAdRepo implements AdRepo {

    @Override
    public ResponseValue<List<AdResponse.Ad>> getAd() {
        return null;
    }
}
