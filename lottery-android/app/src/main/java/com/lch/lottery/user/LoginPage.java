package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.CommonTitleView;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.user.controller.UserController;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class LoginPage extends TabPage {

    private UserController userController = new UserController();
    private UserPage userPage;
    private View loginBT;
    private EditText userAccountET;
    private EditText userPwdET;
    private CommonTitleView common_title;

    public LoginPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
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

                userController.login(username, pwd, new LoginCb(LoginPage.this));
            }
        });
    }


    private static class LoginCb implements UserController.Callback {

        private WeakReference<LoginPage> ref;

        private LoginCb(LoginPage loginPage) {
            ref = new WeakReference<>(loginPage);

        }

        @Override
        public void onSuccess(UserResponse.User data) {
            LoginPage loginPage = ref.get();
            if (loginPage != null) {
                loginPage.userPage.gotoAccountInfo();
            }

        }

        @Override
        public void onFail(String msg) {
            LoginPage loginPage = ref.get();
            if (loginPage != null) {
                ToastUtils.showShort(msg);
            }
        }
    }
}
