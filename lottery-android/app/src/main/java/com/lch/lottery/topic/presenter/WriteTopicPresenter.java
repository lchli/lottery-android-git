package com.lch.lottery.topic.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.domain.AddTopicCase;
import com.lchli.arch.clean.ControllerCallback;
import com.lchli.utils.tool.EventBusUtils;

/**
 * presenter具有更好的可测试性，并且ui更容易变更为其它实现。
 * Created by Administrator on 2019/1/2.
 */

public class WriteTopicPresenter {

    public interface MvpView {
        void showLoading();

        void dismissLoading();

        void showToast(String msg);

        void closeUi();
    }


    private AddTopicCase addTopicCase = new AddTopicCase(TopicModuleInjector.getINS().provideTopicRepo());
    private MvpView view;

    public WriteTopicPresenter(@NonNull MvpView view) {
        this.view = view;
    }

    public void onSaveTopic(String title, String tag, String content, String topicId) {
        AddTopicCase.Param param = new AddTopicCase.Param();

        param.title = title;
        param.tag = tag;
        param.content = content;
        param.topicId = topicId;

        view.showLoading();

        addTopicCase.invokeAsync(param, new ControllerCallback<Void>() {
            @Override
            public void onSuccess(@Nullable Void aVoid) {
                EventBusUtils.post(new TopicListDataChangedEvent());
                view.showToast("发布成功");
                view.dismissLoading();
                view.closeUi();

            }

            @Override
            public void onError(int code, String msg) {
                view.showToast(msg);
                view.dismissLoading();
            }
        });
    }

}
