package com.lch.lottery.user.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.CommonTitleView;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.user.presenter.LoginPresenter;
import com.lch.lottery.util.DialogUtil;
import com.lchli.utils.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class LoginPage extends TabPage implements LoginPresenter.MvpView {

    private UserPage userPage;
    private View loginBT;
    private EditText userAccountET;
    private EditText userPwdET;
    private CommonTitleView common_title;
    private LoginPresenter loginPresenter;
    private Dialog loading;

    public LoginPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        loginPresenter = new LoginPresenter(new ViewProxy(this));

        View.inflate(getContext(), R.layout.fragment_login, this);
        loginBT = VF.f(this, R.id.login_widget);
        userAccountET = VF.f(this, R.id.user_account_edit);
        userPwdET = VF.f(this, R.id.user_pwd_edit);
        common_title = VF.f(this, R.id.common_title);

        common_title.setCenterText(getResources().getString(R.string.login), null);
        common_title.addRightText(getResources().getString(R.string.goto_register), new OnClickListener() {
            @Override
            public void onClick(View v) {
                userPage.gotoRegister();
            }
        });


        loginBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userAccountET.getText().toString();
                String pwd = userPwdET.getText().toString();

                loginPresenter.onLogin(username, pwd);
            }
        });
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
    public void gotoAccountInfoPage() {
        userPage.gotoAccountInfo();
    }


    private static class ViewProxy implements LoginPresenter.MvpView {

        private final WeakReference<LoginPresenter.MvpView> uiRef;

        private ViewProxy(LoginPresenter.MvpView activity) {
            this.uiRef = new WeakReference<>(activity);
        }

        @Override
        public void showLoading(boolean show) {
            final LoginPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoading(show);
            }
        }

        @Override
        public void gotoAccountInfoPage() {
            final LoginPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.gotoAccountInfoPage();
            }
        }


        @Override
        public void showToast(String msg) {
            final LoginPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showToast(msg);
            }
        }


    }


}
