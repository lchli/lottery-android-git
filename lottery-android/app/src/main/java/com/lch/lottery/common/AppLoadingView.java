package com.lch.lottery.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lch.lottery.R;

/**
 * Created by Administrator on 2019/1/8.
 */

public class AppLoadingView extends FrameLayout {

    public AppLoadingView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public AppLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        View.inflate(context, R.layout.common_loading_view, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //intercept click listener.
            }
        });
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(GONE);
    }

}


