package com.lch.lottery.topic.repository;

import com.lch.lottery.topic.model.AdResponse;
import com.lch.netkit.string.ResponseValue;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/28.
 */

public interface AdRepo {

    ResponseValue<AdResponse> getAd();

}
