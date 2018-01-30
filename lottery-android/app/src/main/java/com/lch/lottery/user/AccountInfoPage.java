package com.lch.lottery.user;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.user.controller.UserController;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;
import com.lch.netkit.imageloader.LiImageLoader;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class AccountInfoPage extends TabPage {


    private UserPage userPage;
    private TextView user_nick;
    private ImageView user_portrait;
    private UserController userController = new UserController();

    public AccountInfoPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        View.inflate(getContext(), R.layout.page_account_info, this);
        user_nick = VF.f(this, R.id.user_nick);
        user_portrait = VF.f(this, R.id.user_portrait);

        View logout_widget = VF.f(this, R.id.logout_widget);
        logout_widget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userController.clearCurrentUser(new UserController.ClearCallback() {
                    @Override
                    public void onSuccess() {
                        userPage.gotoLogin();
                    }

                    @Override
                    public void onFail(String msg) {
                        ToastUtils.showShort(msg);

                    }
                });

            }
        });

        LiImageLoader.instance().builder()
                .source("https://www.baidu.com/img/bd_logo1.png")
                .view(user_portrait)
                .circle()
                .display(getContext());

    }

    @Override
    public void onActivityResumed(Activity activity) {
        userController.getCurrentUser(new UserController.Callback() {
            @Override
            public void onSuccess(UserResponse.User data) {
                if (data == null) {
                    return;
                }
                user_nick.setText(data.userName);
            }

            @Override
            public void onFail(String msg) {
                ToastUtils.showShort(msg);

            }
        });


    }


}
