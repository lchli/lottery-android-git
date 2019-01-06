package com.lch.lottery.servicetool;

import com.lch.lottery.servicetool.datainterface.ServiceToolSource;

/**
 * Created by lichenghang on 2019/1/6.
 */

public interface ServiceModuleFactory {

    ServiceToolSource provideServiceToolSource();
}
