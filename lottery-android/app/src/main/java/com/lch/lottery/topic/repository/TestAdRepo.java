package com.lch.lottery.topic.repository;

import com.lch.lottery.topic.datainterface.AdRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.netkit.string.ResponseValue;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/31.
 */

public class TestAdRepo implements AdRepo {

    @Override
    public ResponseValue<AdResponse> getAd() {
        ResponseValue<AdResponse> res = new ResponseValue<>();

        AdResponse adResponse = new AdResponse();
        adResponse.data = new ArrayList<>();

        AdResponse.Ad ad = new AdResponse.Ad();
        ad.imgUrl = "https://www.baidu.com/img/bd_logo1.png";
        adResponse.data.add(ad);

        ad = new AdResponse.Ad();
        ad.imgUrl = "https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=3756930651,2591929300&fm=173&s=A4006DB54A23149C5F9981060300D0C1&w=218&h=146&img.JPEG";
        adResponse.data.add(ad);

        res.data = adResponse;

        return res;
    }
}
