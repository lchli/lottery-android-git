package com.lch.lottery.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.user.controller.UserController;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class UserPage extends TabPage {


    private ViewFlipper pagesVF;
    private LoginPage mLoginPage;
    private RegisterPage mRegisterPage;
    private AccountInfoPage mAccountInfoPage;
    private UserController userController = new UserController();

    public UserPage(@NonNull Context context) {
        super(context);
        init();
    }

    public UserPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void init() {
        View.inflate(getContext(), R.layout.page_user, this);
        pagesVF = VF.f(this, R.id.pagesVF);
        pagesVF.setInAnimation(getContext(), R.anim.slide_in_right);
        pagesVF.setOutAnimation(getContext(), R.anim.slide_out_left);

        mLoginPage = new LoginPage(getContext(), this);
        mRegisterPage = new RegisterPage(getContext(), this);
        mAccountInfoPage = new AccountInfoPage(getContext(), this);


        pagesVF.addView(mLoginPage);
        pagesVF.addView(mRegisterPage);
        pagesVF.addView(mAccountInfoPage);

        userController.getCurrentUser(new UserController.Callback() {
            @Override
            public void onSuccess(UserResponse.User data) {
                if (data != null) {
                    gotoAccountInfo();
                } else {
                    gotoLogin();
                }
            }

            @Override
            public void onFail(String msg) {
                ToastUtils.showShort(msg);

            }
        });


    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mLoginPage.onActivityCreated(activity, savedInstanceState);
        mRegisterPage.onActivityCreated(activity, savedInstanceState);
        mAccountInfoPage.onActivityCreated(activity, savedInstanceState);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mLoginPage.onActivityDestroyed(activity);
        mRegisterPage.onActivityDestroyed(activity);
        mAccountInfoPage.onActivityDestroyed(activity);
    }


    public void gotoLogin() {
        pagesVF.setDisplayedChild(0);
    }

    public void gotoRegister() {
        pagesVF.setDisplayedChild(1);
    }

    public void gotoAccountInfo() {
        pagesVF.setDisplayedChild(2);
        mAccountInfoPage.onActivityResumed(null);
    }
}
