package com.lch.lottery.user.domain;

import com.lch.lottery.user.datainterface.UserSessionSource;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class ClearUserSessionCase extends UseCase<Void, Void> {


    private UserSessionSource userSessionSource;

    public ClearUserSessionCase(UserSessionSource userSessionSource) {
        this.userSessionSource = userSessionSource;
    }

    @Override
    protected ResponseValue<Void> execute(Void parameters) {

        return userSessionSource.clearSession();
    }
}
