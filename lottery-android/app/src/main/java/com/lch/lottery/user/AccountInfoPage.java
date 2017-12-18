package com.lch.lottery.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.lch.lottery.R;
import com.lch.lottery.TabPage;
import com.lch.lottery.user.data.UserRepo;
import com.lch.lottery.user.model.UserResponse;
import com.lch.netkit.common.tool.VF;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class AccountInfoPage extends TabPage {


    private UserPage userPage;
    private TextView user_nick;

    public AccountInfoPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        View.inflate(getContext(), R.layout.page_account_info, this);
        user_nick = VF.f(this, R.id.user_nick);

    }

    @Override
    public void onCreateImpl() {
        super.onCreateImpl();
    }

    @Override
    public void onDestroyImpl() {
        super.onDestroyImpl();
    }

    @Override
    public void refresh() {
        UserResponse.User session = UserRepo.getLoginUser();
        if (session == null) {
            return;
        }
        user_nick.setText(session.userName);
    }
}
