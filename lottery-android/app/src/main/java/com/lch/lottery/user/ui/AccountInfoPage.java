package com.lch.lottery.user.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.BuildConfig;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.eventbus.SwitchLotteryEvent;
import com.lch.lottery.user.presenter.AccountInfoPresenter;
import com.lch.lottery.user.widget.UserCenterListItem;
import com.lch.lottery.util.DialogUtil;
import com.lchli.imgloader.ImgLoaderManager;
import com.lchli.imgloader.ImgSource;
import com.lchli.utils.tool.EventBusUtils;
import com.lchli.utils.tool.VF;

import java.lang.ref.WeakReference;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class AccountInfoPage extends TabPage implements AccountInfoPresenter.MvpView {


    private UserPage userPage;
    private TextView user_nick;
    private ImageView user_portrait;
    private AccountInfoPresenter accountInfoPresenter;
    private Dialog loading;
    private UserCenterListItem logout_widget;
    private UserCenterListItem switchLotteryView;

    public AccountInfoPage(@NonNull Context context, UserPage userPage) {
        super(context);
        this.userPage = userPage;
        init();
    }


    protected void init() {
        accountInfoPresenter = new AccountInfoPresenter(new ViewProxy(this));

        View.inflate(getContext(), R.layout.page_account_info, this);
        user_nick = VF.f(this, R.id.user_nick);
        user_portrait = VF.f(this, R.id.user_portrait);
        switchLotteryView = VF.f(this, R.id.switchLotteryView);

        logout_widget = VF.f(this, R.id.logout_widget);
        UserCenterListItem about_app_widget = VF.f(this, R.id.about_app_widget);
        about_app_widget.setText(getResources().getString(R.string.app_version_info, BuildConfig.VERSION_NAME));

        logout_widget.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                accountInfoPresenter.onLogoutBtClick();

            }
        });

        switchLotteryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBusUtils.post(new SwitchLotteryEvent());

            }
        });


    }

    @Override
    public void onActivityResumed(Activity activity) {
        accountInfoPresenter.onLoadUserInfo();
    }


    @Override
    public void showLoading(boolean show) {
        if (show) {
            if (loading == null) {
                loading = DialogUtil.showLoadingDialog((Activity) getContext());
            }
        } else {
            if (loading != null) {
                loading.dismiss();
                loading = null;
            }
        }

    }

    @Override
    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void showUserNick(String text) {
        user_nick.setText(text);
    }


    @Override
    public void gotoLoginUi() {
        userPage.gotoLogin();
    }

    @Override
    public void showLoginBtText(String text) {
        logout_widget.setText(text);
    }

    @Override
    public void showUserHead(String path) {
        ImgLoaderManager.getINS().display(user_portrait, ImgSource.create().setImgUri(Uri.parse(path)), null);

    }

    @Override
    public void showUserHead(int resId) {
        user_portrait.setImageResource(resId);
    }

    private static class ViewProxy implements AccountInfoPresenter.MvpView {

        private final WeakReference<AccountInfoPresenter.MvpView> uiRef;

        private ViewProxy(AccountInfoPresenter.MvpView activity) {
            this.uiRef = new WeakReference<>(activity);
        }

        @Override
        public void showLoading(boolean show) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoading(show);
            }
        }

        @Override
        public void showUserNick(String text) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showUserNick(text);
            }
        }

        @Override
        public void gotoLoginUi() {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.gotoLoginUi();
            }
        }


        @Override
        public void showToast(String msg) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showToast(msg);
            }
        }

        @Override
        public void showLoginBtText(String text) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoginBtText(text);
            }
        }

        @Override
        public void showUserHead(String path) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showUserHead(path);
            }
        }

        @Override
        public void showUserHead(int resId) {
            final AccountInfoPresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showUserHead(resId);
            }
        }
    }
}
