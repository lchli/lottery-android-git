package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class RegisterPage extends TabPage implements RegisterContract.View {

    private final UserPage userPage;
    private View gotoLoginBT;
    private View registerBT;
    private EditText userAccountET;
    private EditText userPwdET;
    private EditText userRePwdET;
    private RegisterContract.Presenter presenter = new RegisterContract.PresenterImpl();

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
                String username = userAccountET.getText().toString();
                String pwd = userPwdET.getText().toString();
                String repwd = userRePwdET.getText().toString();

                presenter.register(username, pwd, repwd);

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

    }
}
