package com.lch.lottery.user.repository;

import com.blankj.utilcode.util.SPUtils;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.JsonHelper;
import com.lch.netkit.string.ResponseValue;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class LocalUserRepo implements UserRepo {

    private static final String USER_SP_FILE = "user_sp_file";
    private static final String KEY_USER = "KEY_USER";

    @Override
    public ResponseValue<UserResponse.User> get(String username, String pwd) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ResponseValue<UserResponse.User> add(UserResponse.User user) {
        SPUtils.getInstance(USER_SP_FILE).put(KEY_USER, JsonHelper.toJSONString(user));

        ResponseValue<UserResponse.User> responseValue = new ResponseValue<>();
        responseValue.data = user;
        return responseValue;
    }

    @Override
    public ResponseValue<UserResponse.User> getCurrentUser() {
        String userJson = SPUtils.getInstance(USER_SP_FILE).getString(KEY_USER);

        ResponseValue<UserResponse.User> responseValue = new ResponseValue<>();
        responseValue.data = JsonHelper.parseObject(userJson, UserResponse.User.class);
        return responseValue;
    }

    @Override
    public ResponseValue deleteCurrentUser() {
        SPUtils.getInstance(USER_SP_FILE).put(KEY_USER, "");

        return new ResponseValue();
    }


    @Override
    public ResponseValue<UserResponse.User> update(UserResponse.User user) {
        return add(user);
    }
}
