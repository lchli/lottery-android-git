package com.lch.lottery.user;

import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.datainterface.UserSessionSource;

/**
 * Created by Administrator on 2019/1/4.
 */

public class UserModuleInjector implements UserModuleFactory {
    private static final UserModuleInjector INS = new UserModuleInjector();
    private UserModuleFactory impl;

    public static UserModuleInjector getINS() {
        return INS;
    }

    public void initFactory(UserModuleFactory factory) {
        impl = factory;
    }

    @Override
    public RemoteUserSource provideRemoteUserSource() {
        return impl.provideRemoteUserSource();
    }

    @Override
    public UserSessionSource provideUserSessionSource() {
        return impl.provideUserSessionSource();
    }
}
