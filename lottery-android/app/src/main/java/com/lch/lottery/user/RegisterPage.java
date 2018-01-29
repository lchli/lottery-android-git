package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.user.controller.UserController;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class RegisterPage extends TabPage {

    private final UserPage userPage;
    private View gotoLoginBT;
    private View registerBT;
    private EditText userAccountET;
    private EditText userPwdET;
    private EditText userRePwdET;
    private UserController userController = new UserController();

    public RegisterPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        View.inflate(getContext(), R.layout.page_register, this);
        gotoLoginBT = VF.f(this, R.id.gotoLoginBT);
        registerBT = VF.f(this, R.id.registerBT);
        userAccountET = VF.f(this, R.id.userAccountET);
        userPwdET = VF.f(this, R.id.userPwdET);
        userRePwdET = VF.f(this, R.id.userRePwdET);


        gotoLoginBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userPage.gotoLogin();
            }
        });
        registerBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserResponse.User user = new UserResponse.User();

                user.userName = userAccountET.getText().toString();
                user.userPwd = userPwdET.getText().toString();
                String repwd = userRePwdET.getText().toString();

                if (!user.userPwd.equals(repwd)) {
                    ToastUtils.showShort("两次密码不一致");
                    return;
                }

                userController.register(user, new RegisterCb(RegisterPage.this));

            }
        });
    }


    private static class RegisterCb implements UserController.Callback {

        private WeakReference<RegisterPage> ref;

        private RegisterCb(RegisterPage loginPage) {
            ref = new WeakReference<>(loginPage);

        }

        @Override
        public void onSuccess(UserResponse.User data) {
            RegisterPage loginPage = ref.get();
            if (loginPage != null) {
                loginPage.userPage.gotoAccountInfo();
            }

        }

        @Override
        public void onFail(String msg) {
            RegisterPage loginPage = ref.get();
            if (loginPage != null) {
                ToastUtils.showShort(msg);
            }
        }
    }
}
