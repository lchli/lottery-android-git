package com.lch.lottery.user.repository;

import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.string.ResponseValue;

/**
 * Created by lichenghang on 2018/1/29.
 */

public interface UserRepo {

    ResponseValue<UserResponse.User> get(String username, String pwd);

    ResponseValue<UserResponse.User> add(UserResponse.User user);

    ResponseValue<UserResponse.User> update(UserResponse.User user);

    ResponseValue<UserResponse.User> getCurrentUser();

    ResponseValue deleteCurrentUser();


}
