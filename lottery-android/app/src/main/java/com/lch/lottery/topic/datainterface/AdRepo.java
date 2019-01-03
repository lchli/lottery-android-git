package com.lch.lottery.topic.datainterface;

import com.lch.lottery.topic.model.AdResponse;
import com.lchli.arch.clean.ResponseValue;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface AdRepo {

    ResponseValue<AdResponse> getAd();

}
