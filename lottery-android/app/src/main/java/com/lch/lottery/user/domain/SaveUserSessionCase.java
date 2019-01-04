package com.lch.lottery.user.domain;

import com.lch.lottery.user.datainterface.UserSessionSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class SaveUserSessionCase extends UseCase<SaveUserSessionCase.Param, Void> {

    public static class Param {
        public UserResponse.User session;
    }

    private UserSessionSource userSessionSource;

    public SaveUserSessionCase(UserSessionSource userSessionSource) {
        this.userSessionSource = userSessionSource;
    }

    @Override
    protected ResponseValue<Void> execute(Param parameters) {
        if (parameters.session == null) {
            return new ResponseValue<Void>().setErrorMsg("参数不能为空！");
        }
        return userSessionSource.saveSession(parameters.session);
    }
}
