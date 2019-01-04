package com.lch.lottery.user.presenter;

import android.support.annotation.NonNull;

import com.lch.lottery.user.UserModuleInjector;
import com.lch.lottery.user.domain.RegisterCase;
import com.lch.lottery.user.domain.SaveUserSessionCase;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by Administrator on 2019/1/4.
 */

public class RegisterPresenter {
    public interface MvpView {
        void showLoading(boolean show);

        void showToast(String msg);

        void gotoAccountInfoPage();
    }

    private MvpView mvpView;
    private RegisterCase registerCase = new RegisterCase(UserModuleInjector.getINS().provideRemoteUserSource());
    private SaveUserSessionCase saveUserSessionCase = new SaveUserSessionCase(UserModuleInjector.getINS().provideUserSessionSource());

    public RegisterPresenter(@NonNull MvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void onRegister(String userName, String pwd, String repwd) {
        if (!pwd.equals(repwd)) {
            mvpView.showToast("两次密码不一致");
            return;
        }

        mvpView.showLoading(true);

        final RegisterCase.Param param = new RegisterCase.Param();
        param.userName = userName;
        param.userPwd = pwd;

        UseCase.executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final ResponseValue<UserResponse.User> reg = registerCase.invokeSync(param);
                if (reg.hasError() || reg.data == null) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mvpView.showToast(reg.getErrorMsg());
                            mvpView.showLoading(false);
                        }
                    });
                    return;
                }

                final SaveUserSessionCase.Param seParam = new SaveUserSessionCase.Param();
                seParam.session = reg.data;

                final ResponseValue<Void> se = saveUserSessionCase.invokeSync(seParam);
                if (se.hasError()) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mvpView.showToast(se.getErrorMsg());
                            mvpView.showLoading(false);
                        }
                    });
                    return;
                }

                UseCase.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mvpView.showLoading(false);
                        mvpView.gotoAccountInfoPage();
                    }
                });

            }
        });
    }
}
