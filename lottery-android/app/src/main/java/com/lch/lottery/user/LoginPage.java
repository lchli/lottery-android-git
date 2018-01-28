package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.lottery.user.model.UserResponse;
import com.lch.lottery.user.presenter.LoginContract;
import com.lch.netkit.common.tool.VF;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class LoginPage extends TabPage implements LoginContract.View {

    private LoginContract.Presenter presenter = new LoginContract.PresenterImpl();
    private UserPage userPage;
    private View gotoRegisterBT;
    private View loginBT;
    private EditText userAccountET;
    private EditText userPwdET;

    public LoginPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        View.inflate(getContext(), R.layout.page_login, this);
        gotoRegisterBT = VF.f(this, R.id.gotoRegisterBT);
        loginBT = VF.f(this, R.id.loginBT);
        userAccountET = VF.f(this, R.id.userAccountET);
        userPwdET = VF.f(this, R.id.userPwdET);
        gotoRegisterBT.setOnClickListener(new OnClickListener() {
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

                presenter.login(username, pwd);
            }
        });
    }


    @Override
    public void onCreateImpl() {
        if (isCreated) {
            return;
        }
        isCreated = true;

        presenter.registerView(this);
    }

    @Override
    public void onDestroyImpl() {
        if (isDestroyed) {
            return;
        }
        isDestroyed = true;

        presenter.unregisterView();

    }

    @Override
    public void onSuccess(UserResponse.User data) {
        userPage.gotoAccountInfo();

    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShort(msg);
    }
}
