package com.lch.lottery.user.domain;

import com.lch.lottery.user.datainterface.UserSessionSource;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class GetUserSessionCase extends UseCase<Void, UserResponse.User> {

    private UserSessionSource userSessionSource;

    public GetUserSessionCase(UserSessionSource userSessionSource) {
        this.userSessionSource = userSessionSource;
    }

    @Override
    protected ResponseValue<UserResponse.User> execute(Void parameters) {

        return userSessionSource.getSession();
    }
}
