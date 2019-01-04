package com.lch.lottery.user.presenter;

import android.support.annotation.Nullable;

import com.lch.lottery.user.UserModuleInjector;
import com.lch.lottery.user.domain.GetUserSessionCase;
import com.lch.lottery.user.model.UserResponse;
import com.lchli.arch.clean.ControllerCallback;

/**
 * Created by lichenghang on 2019/1/4.
 */

public class UserPagePresenter {

    public interface MvpView {
        void showLoading(boolean show);

        void showToast(String msg);

        void gotoLoginUi();

        void gotoAccountInfo();
    }

    private MvpView mvpView;

    private GetUserSessionCase getUserSessionCase = new GetUserSessionCase(UserModuleInjector.getINS().provideUserSessionSource());

    public UserPagePresenter(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void onLoadUserInfo() {
        mvpView.showLoading(true);

        getUserSessionCase.invokeAsync(null, new ControllerCallback<UserResponse.User>() {
            @Override
            public void onSuccess(@Nullable UserResponse.User user) {
                mvpView.showLoading(false);

                if (user != null) {
                    mvpView.gotoAccountInfo();
                } else {
                    mvpView.gotoLoginUi();
                }

            }

            @Override
            public void onError(int code, String msg) {
                mvpView.showLoading(false);
                mvpView.showToast(msg);

            }
        });


    }


}
