package com.lch.lottery.user.data;

import com.blankj.utilcode.util.SPUtils;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.JsonHelper;

/**
 * Created by lichenghang on 2017/12/16.
 */

public final class UserRepo {

    private static final String USER_SP_FILE = "user_sp_file";
    private static final String KEY_USER = "KEY_USER";

    public static UserResponse.User getLoginUser() {
        String userJson = SPUtils.getInstance(USER_SP_FILE).getString(KEY_USER);
        return JsonHelper.parseObject(userJson, UserResponse.User.class);

    }

    public static void saveUser(UserResponse.User user) {
        SPUtils.getInstance(USER_SP_FILE).put(KEY_USER, JsonHelper.toJSONString(user));
    }
}
