package com.lch.lottery.servicetool.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.R;
import com.lch.lottery.common.TabPage;
import com.lch.lottery.filter.GridDividerDecoration;
import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lch.lottery.servicetool.presenter.ServicePagePresenter;
import com.lch.lottery.util.DialogUtil;
import com.lchli.utils.tool.VF;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by lichenghang on 2019/1/4.
 */

public class ServicePage extends TabPage implements ServicePagePresenter.MvpView {
    private final ServiceToolAdapter mServiceToolAdapter;
    private final Dialog dialog;
    private RecyclerView serviceGridView;
    private ServicePagePresenter servicePagePresenter;

    public ServicePage(@NonNull Context context) {
        super(context);
        View.inflate(context, R.layout.page_service, this);

        serviceGridView = VF.f(this, R.id.serviceGridView);
        serviceGridView.setHasFixedSize(true);
        serviceGridView.setLayoutManager(new GridLayoutManager(context, 2));
        serviceGridView.addItemDecoration(new GridDividerDecoration(context));

        mServiceToolAdapter = new ServiceToolAdapter();
        servicePagePresenter = new ServicePagePresenter(new ViewProxy(this));
        serviceGridView.setAdapter(mServiceToolAdapter);

        dialog = DialogUtil.createDialog((Activity) context);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        super.onActivityCreated(activity, savedInstanceState);

        servicePagePresenter.onStartLoad();
    }


    @Override
    public void onActivityDestroyed(Activity activity) {
        super.onActivityDestroyed(activity);
    }

    @Override
    public void showTools(List<ServiceTool> datas) {
        mServiceToolAdapter.refresh(datas);

    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showShort(msg);

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }

    }


    private static class ViewProxy implements ServicePagePresenter.MvpView {

        private final WeakReference<ServicePagePresenter.MvpView> uiRef;

        private ViewProxy(ServicePagePresenter.MvpView activity) {
            this.uiRef = new WeakReference<>(activity);
        }

        @Override
        public void showLoading(boolean show) {
            final ServicePagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showLoading(show);
            }
        }

        @Override
        public void showTools(List<ServiceTool> datas) {
            final ServicePagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showTools(datas);
            }
        }

        @Override
        public void showFail(String msg) {
            final ServicePagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showFail(msg);
            }

        }

        @Override
        public void showEmpty() {
            final ServicePagePresenter.MvpView ui = uiRef.get();
            if (ui != null) {
                ui.showEmpty();
            }

        }
    }
}
