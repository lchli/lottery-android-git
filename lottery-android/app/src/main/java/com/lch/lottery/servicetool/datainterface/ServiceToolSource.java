package com.lch.lottery.servicetool.datainterface;

import com.lchli.arch.clean.ResponseValue;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public interface ServiceToolSource {

    ResponseValue<List<ServiceTool>>  getTools();
}
