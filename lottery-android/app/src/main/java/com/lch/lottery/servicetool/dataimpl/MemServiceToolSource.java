package com.lch.lottery.servicetool.dataimpl;

import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.filter.FilterActivity;
import com.lch.lottery.map.PoiSearchActivity;
import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lch.lottery.servicetool.datainterface.ServiceToolSource;
import com.lchli.arch.clean.ResponseValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public class MemServiceToolSource implements ServiceToolSource {

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
