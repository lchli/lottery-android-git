package com.lch.lottery.user.presenter;

import android.support.annotation.Nullable;

import com.lch.lottery.R;
import com.lch.lottery.user.UserModuleInjector;
import com.lch.lottery.user.domain.ClearUserSessionCase;
import com.lch.lottery.user.domain.GetUserSessionCase;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ControllerCallback;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

/**
 * Created by lichenghang on 2019/1/4.
 */

public class AccountInfoPresenter {

    public interface MvpView {
        void showLoading(boolean show);

        void showToast(String msg);

        void showUserNick(String text);

        void showLoginBtText(String text);

        void showUserHead(String path);

        void showUserHead(int resId);

        void gotoLoginUi();
    }

    private MvpView mvpView;

    private GetUserSessionCase getUserSessionCase = new GetUserSessionCase(UserModuleInjector.getINS().provideUserSessionSource());
    private ClearUserSessionCase clearUserSessionCase = new ClearUserSessionCase(UserModuleInjector.getINS().provideUserSessionSource());

    public AccountInfoPresenter(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void onLoadUserInfo() {
        mvpView.showLoading(true);

        getUserSessionCase.invokeAsync(null, new ControllerCallback<UserResponse.User>() {
            @Override
            public void onSuccess(@Nullable UserResponse.User user) {
                mvpView.showLoading(false);
                if (user != null) {
                    mvpView.showUserNick(user.userName);
                    mvpView.showUserHead("https://www.baidu.com/img/bd_logo1.png");
                    mvpView.showLoginBtText("退出登录");
                } else {
                    mvpView.showUserNick("");
                    mvpView.showUserHead(R.drawable.default_head);
                    mvpView.showLoginBtText("去登录");
                }

            }

            @Override
            public void onError(int code, String msg) {
                mvpView.showLoading(false);
                mvpView.showToast(msg);

            }
        });


    }

    public void onLogoutBtClick() {
        mvpView.showLoading(true);

        UseCase.executeOnDiskIO(new Runnable() {
            @Override
            public void run() {
                final ResponseValue<UserResponse.User> res = getUserSessionCase.invokeSync(null);
                if (res.hasError()) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mvpView.showLoading(false);
                            mvpView.showToast(res.getErrorMsg());
                        }
                    });
                    return;
                }

                if (res.data == null) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mvpView.showLoading(false);
                            mvpView.gotoLoginUi();
                        }
                    });
                    return;
                }

                final ResponseValue<Void> clearRes = clearUserSessionCase.invokeSync(null);
                if (clearRes.hasError()) {
                    UseCase.executeOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            mvpView.showLoading(false);
                            mvpView.showToast(clearRes.getErrorMsg());
                        }
                    });
                    return;
                }

                UseCase.executeOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mvpView.showLoading(false);

                        mvpView.showUserNick("");
                        mvpView.showUserHead(R.drawable.default_head);
                        mvpView.showLoginBtText("去登录");
                    }
                });


            }
        });


    }

}
