package com.lch.lottery.common;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lch.lottery.R;


/**
 * 通用的空页面。
 * Created by lichenghang on 2016/4/5.
 */
public class AppEmptyView extends FrameLayout {

    public static final int DEF_TEXT_SIZE_SP = 16;
    public static final int DEF_TEXT_COLOR = Color.parseColor("#646464");
    private LinearLayout rootView;

    public AppEmptyView(Context context) {
        super(context);
        init();
    }

    public AppEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AppEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.common_empty_view, this);
        rootView = (LinearLayout) findViewById(R.id.rootView);
    }

    public void addEmptyText(String text) {
        addEmptyText(text, null);
    }

    public void addEmptyText(String text, OnClickListener lsn) {
        TextView tv = new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(DEF_TEXT_SIZE_SP);
        tv.setTextColor(DEF_TEXT_COLOR);
        tv.setOnClickListener(lsn);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        rootView.addView(tv, lp);

    }

    public void addEmptyImage(int imageResId) {
        addEmptyImage(imageResId, null);
    }

    public void addEmptyImage(int imageResId, OnClickListener lsn) {
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(imageResId);
        iv.setOnClickListener(lsn);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        rootView.addView(iv, lp);
    }

    public void show() {
        rootView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        rootView.setVisibility(View.GONE);
    }
}
