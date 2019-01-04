package com.lch.lottery.user.dataimpl;

import com.blankj.utilcode.util.SPUtils;
import com.lch.lottery.user.datainterface.UserSessionSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.utils.tool.JsonHelper;

/**
 * Created by Administrator on 2019/1/4.
 */

public class SpUserSessionSource implements UserSessionSource {
    private static final String USER_SP_FILE = "user_sp_file";
    private static final String KEY_USER = "KEY_USER";

    @Override
    public ResponseValue<UserResponse.User> getSession() {
        String userJson = SPUtils.getInstance(USER_SP_FILE).getString(KEY_USER);

        ResponseValue<UserResponse.User> responseValue = new ResponseValue<>();
        responseValue.data = JsonHelper.parseObject(userJson, UserResponse.User.class);
        return responseValue;
    }

    @Override
    public ResponseValue saveSession(UserResponse.User user) {
        SPUtils.getInstance(USER_SP_FILE).put(KEY_USER, JsonHelper.toJSONString(user));
        return new ResponseValue();
    }

    @Override
    public ResponseValue clearSession() {
        SPUtils.getInstance(USER_SP_FILE).put(KEY_USER, "");
        return new ResponseValue();
    }
}
