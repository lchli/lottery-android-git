package com.lch.lottery.servicetool.presenter;

import android.support.annotation.Nullable;

import com.lch.lottery.servicetool.ServiceModuleInjector;
import com.lch.lottery.servicetool.datainterface.ServiceTool;
import com.lch.lottery.servicetool.domain.GetServiceToolCase;
import com.lchli.arch.clean.ControllerCallback;
import com.lchli.utils.tool.ListUtils;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/6.
 */

public class ServicePagePresenter {

    public interface MvpView {

        void showTools(List<ServiceTool> datas);

        void showFail(String msg);

        void showEmpty();

        void showLoading(boolean show);
    }

    private GetServiceToolCase getServiceToolCase = new GetServiceToolCase(ServiceModuleInjector.getINS().provideServiceToolSource());
    private MvpView mvpView;

    public ServicePagePresenter(MvpView mvpView) {
        this.mvpView = mvpView;
    }

    public void onStartLoad() {

        mvpView.showLoading(true);

        getServiceToolCase.invokeAsync(null, new ControllerCallback<List<ServiceTool>>() {
            @Override
            public void onSuccess(@Nullable List<ServiceTool> serviceTools) {
                mvpView.showLoading(false);

                if (ListUtils.isEmpty(serviceTools)) {
                    mvpView.showEmpty();
                } else {
                    mvpView.showTools(serviceTools);
                }

            }

            @Override
            public void onError(int code, String msg) {
                mvpView.showLoading(false);
                mvpView.showFail(msg);
            }
        });

    }


}
