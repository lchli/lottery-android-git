package com.lch.lottery.user.datainterface;

import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;

/**
 * Created by Administrator on 2019/1/4.
 */

public interface RemoteUserSource {
    ResponseValue<UserResponse.User> query(String username, String pwd);

    ResponseValue<UserResponse.User> add(UserResponse.User user);

    ResponseValue<UserResponse.User> update(UserResponse.User user);
}
