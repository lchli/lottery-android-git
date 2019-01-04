package com.lch.lottery.user.datainterface;

import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;

/**
 * Created by Administrator on 2019/1/4.
 */

public interface UserSessionSource {
    ResponseValue<UserResponse.User> getSession();

    ResponseValue saveSession(UserResponse.User user);

    ResponseValue clearSession();
}
