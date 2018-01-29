package com.lch.lottery.topic;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.eventbus.TopicListDataChangedEvent;
import com.lch.lottery.topic.controller.TopicController;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.util.DialogUtil;
import com.lch.lottery.util.EventBusUtils;
import com.lch.netkit.common.base.BaseCompatActivity;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class WriteOrEditTopicActivity extends BaseCompatActivity {

    private static final String INTENT_KEY_TOPIC = "topic";
    private TopicController presenter = new TopicController();

    private TextView exitWriteTV;
    private TextView saveWriteTV;
    private EditText topicTitleEt;
    private EditText topicTagEt;
    private EditText topicContentEt;
    private TopicResponse.Topic topic;
    private Dialog loadingDialog;

    public static void launch(@Nullable TopicResponse.Topic topic, Context context) {
        Intent it = new Intent(context, WriteOrEditTopicActivity.class);
        it.putExtra(INTENT_KEY_TOPIC, topic);
        App.launchIt(it);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topic = (TopicResponse.Topic) getIntent().getSerializableExtra(INTENT_KEY_TOPIC);

        setContentView(R.layout.activity_write_or_edit_topic);
        exitWriteTV = f(R.id.exitWriteTV);
        saveWriteTV = f(R.id.saveWriteTV);
        topicTitleEt = f(R.id.topicTitleEt);
        topicTagEt = f(R.id.topicTagEt);
        topicContentEt = f(R.id.topicContentEt);

        if (topic != null) {
            topicTitleEt.setText(topic.title);
            topicTagEt.setText(topic.tag);
            topicContentEt.setText(topic.content);
        }

        exitWriteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        saveWriteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = topicTitleEt.getText().toString();
                String tag = topicTagEt.getText().toString();
                String content = topicContentEt.getText().toString();

                if (topic == null) {
                    topic = new TopicResponse.Topic();
                }
                topic.title = title;
                topic.tag = tag;
                topic.content = content;

                loadingDialog = DialogUtil.showLoadingDialog(WriteOrEditTopicActivity.this);
                presenter.addOrUpdateTopic(topic);

            }
        });

        presenter.setCallback(new TopicController.Callback() {
            @Override
            public void onFail(String msg) {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort(msg);

            }

            @Override
            public void onSuccess() {
                EventBusUtils.post(new TopicListDataChangedEvent());

                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
                ToastUtils.showShort("发布成功");
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        presenter.setCallback(null);
        super.onDestroy();
    }


}
