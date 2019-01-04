package com.lch.lottery.user.domain;

import android.text.TextUtils;

import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class LoginCase extends UseCase<LoginCase.Param, UserResponse.User> {

    public static class Param {
        public String userName;
        public String userPwd;
    }

    private RemoteUserSource remoteUserSource;

    public LoginCase(RemoteUserSource remoteUserSource) {
        this.remoteUserSource = remoteUserSource;
    }

    @Override
    protected ResponseValue<UserResponse.User> execute(Param parameters) {
        if (TextUtils.isEmpty(parameters.userName) || TextUtils.isEmpty(parameters.userPwd)) {
            return new ResponseValue<>().setErrorMsg("参数不能为空！");
        }

        return remoteUserSource.query(parameters.userName, parameters.userPwd);
    }
}
