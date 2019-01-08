package com.lch.lottery.home.ui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.EmptyUtils;
import com.lch.lottery.common.TabPage;

import java.util.List;

/**
 * Created by lichenghang on 2018/1/29.
 */

public class HomePageAdapter extends PagerAdapter {

    private List<TabPage> pages;

    public HomePageAdapter(List<TabPage> pages) {
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return EmptyUtils.isEmpty(pages) ? 0 : pages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (EmptyUtils.isEmpty(pages) || position < 0 || position >= pages.size()) {
            return null;
        }
        View page = pages.get(position);
        container.addView(page);

        return page;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
