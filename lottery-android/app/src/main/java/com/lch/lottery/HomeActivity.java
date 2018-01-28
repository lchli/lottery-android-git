package com.lch.lottery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lch.lottery.user.UserPage;
import com.lch.lottery.filter.FilterPage;
import com.lch.lottery.topic.TopicListPage;
import com.lch.netkit.common.base.BaseCompatActivity;

public class HomeActivity extends BaseCompatActivity {

    private ViewGroup mContent;
    private TopicListPage mTopicListFragment;
    private FilterPage mFilterFragment;
    private UserPage mBuyFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {
                    replaceContentPage(mTopicListFragment);
                }
                return true;
                case R.id.navigation_dashboard: {
                    replaceContentPage(mFilterFragment);
                }
                return true;
                case R.id.navigation_notifications: {
                    replaceContentPage(mBuyFragment);
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
        BottomNavigationView navigation = f(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mTopicListFragment = new TopicListPage(this);
        mFilterFragment = new FilterPage(this);
        mBuyFragment = new UserPage(this);

        replaceContentPage(mTopicListFragment);
    }

    private void replaceContentPage(TabPage page) {
        if (mContent.getChildCount() > 0) {
            View old = mContent.getChildAt(0);
            if (old == page) {
                return;
            }
        }

        mContent.removeAllViews();
        mContent.addView(page);
        page.onCreateImpl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTopicListFragment.onDestroyImpl();
        mFilterFragment.onDestroyImpl();
        mBuyFragment.onDestroyImpl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTopicListFragment.refresh();
        mFilterFragment.refresh();
        mBuyFragment.refresh();
    }
}
