package com.lch.lottery.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.filter.FilterPage;
import com.lch.lottery.topic.TopicListPage;
import com.lch.lottery.user.UserPage;
import com.lch.netkit.common.base.BaseCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseCompatActivity {

    private ViewPager mContent;
    private List<TabPage> pages;
    private BottomNavigationView navigation;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    mContent.setCurrentItem(0);
                }
                return true;
                case R.id.navigation_dashboard: {
                    mContent.setCurrentItem(1);
                }
                return true;
                case R.id.navigation_notifications: {
                    mContent.setCurrentItem(2);
                }
                return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContent = f(R.id.content);
        navigation = f(R.id.navigation);

        mContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        pages = new ArrayList<>();
        pages.add(new TopicListPage(this));
        pages.add(new FilterPage(this));
        pages.add(new UserPage(this));

        mContent.setAdapter(new HomePageAdapter(pages));

        for (TabPage page : pages) {
            page.onActivityCreated(this, savedInstanceState);
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (TabPage page : pages) {
            page.onActivityDestroyed(this);
        }

    }


}
