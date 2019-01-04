package com.lch.lottery.user.presenter;

import android.support.annotation.Nullable;

import com.lch.lottery.user.UserModuleInjector;
import com.lch.lottery.user.domain.LoginCase;
import com.lchli.arch.clean.ControllerCallback;

/**
 * Created by lichenghang on 2019/1/4.
 */

public class LoginPresenter {

    public interface MvpView {
        void showLoading(boolean show);

        void showToast(String msg);

        void gotoAccountInfoPage();
    }

    private MvpView mvpView;

    private LoginCase loginCase = new LoginCase(UserModuleInjector.getINS().provideRemoteUserSource(),
            UserModuleInjector.getINS().provideUserSessionSource());

    public LoginPresenter(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void onLogin(String userName, String pwd) {
        mvpView.showLoading(true);

        final LoginCase.Param param = new LoginCase.Param();
        param.userName = userName;
        param.userPwd = pwd;

        loginCase.invokeAsync(param, new ControllerCallback<Void>() {
            @Override
            public void onSuccess(@Nullable Void aVoid) {
                mvpView.showLoading(false);
                mvpView.gotoAccountInfoPage();
            }

            @Override
            public void onError(int code, String msg) {
                mvpView.showToast(msg);
                mvpView.showLoading(false);

            }
        });


    }
}
