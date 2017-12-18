package com.lch.lottery.user.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.lch.lottery.BaseResponse;

import java.io.Serializable;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class UserResponse extends BaseResponse {
    @JSONField(name = "Data")
    public User data;

    public static class User implements Serializable {
        @JSONField(name = "UserId")
        public String userId;
        @JSONField(name = "UserName")
        public String userName;
        @JSONField(name = "UserPwd")
        public String userPwd;
    }
}
