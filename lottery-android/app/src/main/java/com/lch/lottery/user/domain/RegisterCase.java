package com.lch.lottery.user.domain;

import android.text.TextUtils;

import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class RegisterCase extends UseCase<RegisterCase.Param, UserResponse.User> {

    public static class Param {
        public String userName;
        public String userPwd;
    }

    private RemoteUserSource remoteUserSource;

    public RegisterCase(RemoteUserSource remoteUserSource) {
        this.remoteUserSource = remoteUserSource;
    }

    @Override
    protected ResponseValue<UserResponse.User> execute(Param parameters) {
        if (TextUtils.isEmpty(parameters.userName) || TextUtils.isEmpty(parameters.userPwd)) {
            return new ResponseValue<UserResponse.User>().setErrorMsg("参数不能为空！");
        }

        UserResponse.User user = new UserResponse.User();
        user.userName = parameters.userName;
        user.userPwd = parameters.userPwd;

        return remoteUserSource.add(user);
    }
}
