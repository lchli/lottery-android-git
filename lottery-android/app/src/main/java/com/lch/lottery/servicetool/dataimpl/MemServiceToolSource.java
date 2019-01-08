package com.lch.lottery.servicetool.dataimpl;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.filter.ui.FilterActivity;
import com.lch.lottery.lotterysite.PoiSearchActivity;
import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lch.lottery.servicetool.datainterface.ServiceToolSource;
import com.lchli.arch.clean.ResponseValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public class MemServiceToolSource implements ServiceToolSource {
    private Context context;

    public MemServiceToolSource(Context context) {
        this.context = context;
    }

    @Override
    public ResponseValue<List<ServiceTool>> getTools() {

        List<ServiceTool> tools = new ArrayList<>();
        tools.add(new FilterTool());
        tools.add(new LotteryBuyTool());
        tools.add(new HistoryTool());
        tools.add(new ExpertTool());


        return new ResponseValue<List<ServiceTool>>().setData(tools);
    }

    private static class FilterTool implements ServiceTool {
        @Override
        public void launch() {
            App.launchActivity(FilterActivity.class);
        }

        @Override
        public String getDesc() {
            return "缩水";
        }

        @Override
        public int getIconResId() {
            return R.drawable.icon_geo;
        }
    }

    private static class LotteryBuyTool implements ServiceTool {
        @Override
        public void launch() {
            App.launchActivity(PoiSearchActivity.class);
        }

        @Override
        public String getDesc() {
            return "彩票投注站";
        }

        @Override
        public int getIconResId() {
            return R.drawable.icon_geo;
        }
    }


    private static class HistoryTool implements ServiceTool {
        @Override
        public void launch() {
            ToastUtils.showShort("暂未开放！");
        }

        @Override
        public String getDesc() {
            return "历史开奖查询";
        }

        @Override
        public int getIconResId() {
            return R.drawable.icon_geo;
        }
    }

    private static class ExpertTool implements ServiceTool {
        @Override
        public void launch() {
            ToastUtils.showShort("暂未开放！");
        }

        @Override
        public String getDesc() {
            return "专家预测";
        }

        @Override
        public int getIconResId() {
            return R.drawable.icon_geo;
        }
    }
}
