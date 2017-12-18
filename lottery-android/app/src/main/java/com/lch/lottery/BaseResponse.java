package com.lch.lottery;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class BaseResponse implements Serializable {
    @JSONField(name = "Status")
    public int status;
    @JSONField(name = "Message")
    public String message;
}
