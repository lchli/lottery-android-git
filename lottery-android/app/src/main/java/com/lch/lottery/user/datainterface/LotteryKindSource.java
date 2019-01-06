package com.lch.lottery.user.datainterface;

import com.lch.lottery.user.model.LotteryKind;
import com.lchli.arch.clean.ResponseValue;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public interface LotteryKindSource {

    ResponseValue<List<LotteryKind>> getLotteryKinds();

    ResponseValue<LotteryKind> getCurrentLotteryKind();

    ResponseValue<Void> setCurrentLotteryKind(LotteryKind kind);

}
