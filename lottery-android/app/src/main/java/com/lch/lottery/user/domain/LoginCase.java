package com.lch.lottery.user.domain;

import android.text.TextUtils;

import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.datainterface.UserSessionSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class LoginCase extends UseCase<LoginCase.Param, Void> {

    private final UserSessionSource sessionSource;

    public static class Param {
        public String userName;
        public String userPwd;
    }

    private RemoteUserSource remoteUserSource;

    public LoginCase(RemoteUserSource remoteUserSource, UserSessionSource sessionSource) {
        this.remoteUserSource = remoteUserSource;
        this.sessionSource = sessionSource;
    }

    @Override
    protected ResponseValue<Void> execute(Param parameters) {

        if (TextUtils.isEmpty(parameters.userName) || TextUtils.isEmpty(parameters.userPwd)) {
            return new ResponseValue<Void>().setErrorMsg("参数不能为空！");
        }


        ResponseValue<UserResponse.User> res = remoteUserSource.query(parameters.userName, parameters.userPwd);
        if (res.hasError() || res.data == null) {
            return new ResponseValue<Void>().setErrorMsg(res.getErrorMsg()).setCode(res.code);
        }

        return sessionSource.saveSession(res.data);


    }
}
