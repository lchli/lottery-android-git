package com.lch.lottery.user;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lch.lottery.R;
import com.lch.netkit.common.tool.VF;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class UserCenterListItem extends FrameLayout {

    private TextView text_widget;
    private ImageView icon_widget;

    public UserCenterListItem(@NonNull Context context) {
        super(context);
        init(null);
    }

    public UserCenterListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public UserCenterListItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.user_list_item, this);
        text_widget = VF.f(this, R.id.text_widget);
        icon_widget = VF.f(this, R.id.icon_widget);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.UserCenterListItem);
            String text = a.getString(R.styleable.UserCenterListItem_UserCenterListItem_text);
            Drawable icon = a.getDrawable(R.styleable.UserCenterListItem_UserCenterListItem_icon);
            a.recycle();
            if (text != null) {
                setText(text);
            }
            if (icon != null) {
                setImageDrawable(icon);
            }
        }
    }

    public void setText(CharSequence text) {
        text_widget.setText(text);
    }

    public void setImageDrawable(Drawable icon) {
        icon_widget.setImageDrawable(icon);
    }
}
