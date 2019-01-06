package com.lch.lottery.servicetool;

import com.lch.lottery.servicetool.datainterface.ServiceToolSource;

/**
 * Created by Administrator on 2019/1/4.
 */

public class ServiceModuleInjector implements ServiceModuleFactory {
    private static final ServiceModuleInjector INS = new ServiceModuleInjector();
    private ServiceModuleFactory impl;

    public static ServiceModuleInjector getINS() {
        return INS;
    }

    public void initFactory(ServiceModuleFactory factory) {
        impl = factory;
    }


    @Override
    public ServiceToolSource provideServiceToolSource() {
        return impl.provideServiceToolSource();
    }
}
