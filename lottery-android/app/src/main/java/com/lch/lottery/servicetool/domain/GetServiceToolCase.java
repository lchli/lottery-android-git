package com.lch.lottery.servicetool.domain;

import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lch.lottery.servicetool.datainterface.ServiceToolSource;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public class GetServiceToolCase extends UseCase<Void, List<ServiceTool>> {

    private ServiceToolSource source;

    public GetServiceToolCase(ServiceToolSource source) {
        this.source = source;
    }

    @Override
    protected ResponseValue<List<ServiceTool>> execute(Void parameters) {
        return source.getTools();
    }
}
