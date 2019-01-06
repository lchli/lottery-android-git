package com.lch.lottery.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.SwitchLotteryEvent;
import com.lch.lottery.servicetool.ui.ServicePage;
import com.lch.lottery.topic.ui.TopicListPage;
import com.lch.lottery.user.ui.UserPage;
import com.lch.lottery.util.EventBusUtils;
import com.lchli.utils.base.BaseCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBusUtils.register(this);

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
        pages.add(new ServicePage(this));
        pages.add(new UserPage(this));

        mContent.setAdapter(new HomePageAdapter(pages));

        for (TabPage page : pages) {
            page.onActivityCreated(this, savedInstanceState);
        }


    }

    @Override
    public void finish() {
        super.finish();
        EventBusUtils.unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        for (TabPage page : pages) {
            page.onActivityDestroyed(this);
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SwitchLotteryEvent event) {
        finish();

        App.launchActivity(HomeActivity.class);
    }

}
