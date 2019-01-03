package com.lch.lottery.topic.vm;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.TopicModuleInjector;
import com.lch.lottery.topic.domain.AddTopicCase;
import com.lchli.arch.clean.ControllerCallback;
import com.lchli.utils.tool.EventBusUtils;

/**
 * Created by Administrator on 2019/1/2.
 */

public class WriteTopicVm {


    private AddTopicCase addTopicCase = new AddTopicCase(TopicModuleInjector.getINS().provideTopicRepo());
    public MutableLiveData<Boolean> loadingViewState = new MutableLiveData<>();
    public MutableLiveData<String> toastState = new MutableLiveData<>();
    public MutableLiveData<Void> closeUiAction = new MutableLiveData<>();

    public void onSaveTopic(String title, String tag, String content, String topicId) {
        AddTopicCase.Param param = new AddTopicCase.Param();

        param.title = title;
        param.tag = tag;
        param.content = content;
        param.topicId = topicId;

        loadingViewState.postValue(true);


        addTopicCase.invokeAsync(param, new ControllerCallback<Void>() {
            @Override
            public void onSuccess(@Nullable Void aVoid) {
                loadingViewState.postValue(false);
                EventBusUtils.post(new TopicListDataChangedEvent());
                toastState.postValue("发布成功");
                closeUiAction.postValue(null);
            }

            @Override
            public void onError(int code, String msg) {
                loadingViewState.postValue(false);
                toastState.postValue(msg);
            }
        });
    }

}
