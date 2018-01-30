package com.lch.lottery.topic.model;

import com.lch.lottery.common.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class AdResponse extends BaseResponse {

    public List<Ad> data;

    public static class Ad implements Serializable {
        public String imgUrl;
        public String linkUrl;

    }
}
