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
import com.lch.lottery.user.presenter.RegisterPresenter;
import com.lch.lottery.util.DialogUtil;
import com.lchli.utils.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class RegisterPage extends TabPage implements RegisterPresenter.MvpView {

    private final UserPage userPage;
    private View registerBT;
    private EditText userAccountET;
    private EditText userPwdET;
    private EditText userRePwdET;
    private CommonTitleView common_title;
    private RegisterPresenter registerPresenter;
    private Dialog loading;

    public RegisterPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        registerPresenter = new RegisterPresenter(new ViewProxy(this));

        View.inflate(getContext(), R.layout.fragment_register, this);

        registerBT = VF.f(this, R.id.register_widget);
        userAccountET = VF.f(this, R.id.user_account_edit);
        userRePwdET = VF.f(this, R.id.user_repwd_edit);
        userPwdET = VF.f(this, R.id.user_pwd_edit);
        common_title = VF.f(this, R.id.common_title);

        common_title.setCenterText(getResources().getString(R.string.register), null);
        common_title.addRightText(getResources().getString(R.string.goto_login), new OnClickListener() {
            @Override
            public void onClick(View v) {
                userPage.gotoLogin();
            }
        });

        registerBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.onRegister(userAccountET.getText().toString(), userPwdET.getText().toString(), userRePwdET.getText().toString());
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


    @Override
    public void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
        showLoading(false);
    }

    private static class ViewProxy implements RegisterPresenter.MvpView {

        private final WeakReference<RegisterPresenter.MvpView> uiRef;

        private ViewProxy(RegisterPresenter.MvpView activity) {
            this.uiRef = new WeakReference<>(activity);
        }

        @Override
        public void showLoading(boolean show) {
            final RegisterPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoading(show);
            }
        }

        @Override
        public void gotoAccountInfoPage() {
            final RegisterPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.gotoAccountInfoPage();
            }
        }


        @Override
        public void showToast(String msg) {
            final RegisterPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showToast(msg);
            }
        }


    }


}
