package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.lottery.user.data.UserRepo;
import com.lch.netkit.common.tool.VF;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class UserPage extends TabPage {


    private ViewFlipper pagesVF;
    private LoginPage mLoginPage;
    private RegisterPage mRegisterPage;
    private AccountInfoPage mAccountInfoPage;

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

        if (UserRepo.getLoginUser() != null) {
            gotoAccountInfo();
        } else {
            gotoLogin();
        }
    }

    @Override
    public void onCreateImpl() {
        mLoginPage.onCreateImpl();
        mRegisterPage.onCreateImpl();
        mAccountInfoPage.onCreateImpl();
    }

    @Override
    public void onDestroyImpl() {
        mLoginPage.onDestroyImpl();
        mRegisterPage.onDestroyImpl();
        mAccountInfoPage.onDestroyImpl();
    }


    public void gotoLogin() {
        pagesVF.setDisplayedChild(0);
        mLoginPage.refresh();
    }

    public void gotoRegister() {
        pagesVF.setDisplayedChild(1);
        mRegisterPage.refresh();
    }

    public void gotoAccountInfo() {
        pagesVF.setDisplayedChild(2);
        mAccountInfoPage.refresh();
    }
}
