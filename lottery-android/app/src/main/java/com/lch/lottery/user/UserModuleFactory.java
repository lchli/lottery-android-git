package com.lch.lottery.user;

import com.lch.lottery.user.datainterface.RemoteUserSource;
import com.lch.lottery.user.datainterface.UserSessionSource;

/**
 * Created by Administrator on 2019/1/4.
 */

public interface UserModuleFactory {
    RemoteUserSource provideRemoteUserSource();

    UserSessionSource provideUserSessionSource();
}
