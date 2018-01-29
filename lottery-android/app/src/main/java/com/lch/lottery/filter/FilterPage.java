package com.lch.lottery.filter;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class FilterPage extends TabPage {


    public FilterPage(@NonNull Context context) {
        super(context);
        init();
    }

    public FilterPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FilterPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        super.init();
        View.inflate(getContext(), R.layout.page_filter, this);
    }


}
