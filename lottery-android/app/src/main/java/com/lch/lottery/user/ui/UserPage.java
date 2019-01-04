package com.lch.lottery.user.ui;

import android.app.Activity;
import android.app.Dialog;
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
import com.lch.lottery.user.presenter.UserPagePresenter;
import com.lch.lottery.util.DialogUtil;
import com.lchli.utils.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class UserPage extends TabPage implements UserPagePresenter.MvpView {


    private ViewFlipper pagesVF;
    private LoginPage mLoginPage;
    private RegisterPage mRegisterPage;
    private AccountInfoPage mAccountInfoPage;
    private UserPagePresenter userPagePresenter;
    private Dialog loading;


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
        userPagePresenter = new UserPagePresenter(new ViewProxy(this));

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

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mLoginPage.onActivityCreated(activity, savedInstanceState);
        mRegisterPage.onActivityCreated(activity, savedInstanceState);
        mAccountInfoPage.onActivityCreated(activity, savedInstanceState);

        userPagePresenter.onLoadUserInfo();
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

    @Override
    public void gotoAccountInfo() {
        pagesVF.setDisplayedChild(2);
        mAccountInfoPage.onActivityResumed(null);
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            if (loading == null) {
                loading = DialogUtil.showLoadingDialog((Activity) getContext());
            }
        } else {
            if (loading != null) {
                loading.dismiss();
                loading = null;
            }
        }

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void gotoLoginUi() {
        gotoLogin();
    }


    private static class ViewProxy implements UserPagePresenter.MvpView {

        private final WeakReference<UserPagePresenter.MvpView> uiRef;

        private ViewProxy(UserPagePresenter.MvpView activity) {
            this.uiRef = new WeakReference<>(activity);
        }

        @Override
        public void showLoading(boolean show) {
            final UserPagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoading(show);
            }
        }

        @Override
        public void gotoAccountInfo() {
            final UserPagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.gotoAccountInfo();
            }
        }

        @Override
        public void gotoLoginUi() {
            final UserPagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.gotoLoginUi();
            }
        }


        @Override
        public void showToast(String msg) {
            final UserPagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showToast(msg);
            }
        }


    }
}
