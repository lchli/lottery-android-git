package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.netkit.common.tool.VF;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class LoginPage extends TabPage {


    private UserPage userPage;
    private View gotoRegisterBT;
    private View loginBT;

    public LoginPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        View.inflate(getContext(), R.layout.page_login, this);
        gotoRegisterBT = VF.f(this, R.id.gotoRegisterBT);
        loginBT = VF.f(this, R.id.loginBT);
        gotoRegisterBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userPage.gotoRegister();
            }
        });
        loginBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userPage.gotoAccountInfo();
            }
        });
    }
}
