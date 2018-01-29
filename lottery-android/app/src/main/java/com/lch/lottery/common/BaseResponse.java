package com.lch.lottery.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class BaseResponse implements Serializable {
    @JSONField(name = "status")
    public int status;
    @JSONField(name = "message")
    public String message;
}
