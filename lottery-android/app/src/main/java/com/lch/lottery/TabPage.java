package com.lch.lottery;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TabPage extends FrameLayout {
    protected boolean isCreated = false;
    protected boolean isDestroyed = false;

    public TabPage(@NonNull Context context) {
        super(context);
    }

    public TabPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {

    }


    public void onCreateImpl() {
    }

    public void onDestroyImpl() {
    }

    public void refresh(){}
}
