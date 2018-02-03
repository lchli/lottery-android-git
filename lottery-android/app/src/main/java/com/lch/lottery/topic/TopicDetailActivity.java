package com.lch.lottery.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.netkit.common.base.BaseCompatActivity;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class TopicDetailActivity extends BaseCompatActivity {

    private static final String INTENT_KEY_TOPIC = "topic";


    private TextView exitWriteTV;
    private TextView saveWriteTV;
    private TextView topicTitleEt;
    private TextView topicContentEt;
    private TopicResponse.Topic topic;

    public static void launch(@NonNull TopicResponse.Topic topic, Context context) {
        Intent it = new Intent(context, TopicDetailActivity.class);
        it.putExtra(INTENT_KEY_TOPIC, topic);
        App.launchIt(it);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topic = (TopicResponse.Topic) getIntent().getSerializableExtra(INTENT_KEY_TOPIC);

        setContentView(R.layout.activity_topic_detail);

        exitWriteTV = f(R.id.exitWriteTV);
        saveWriteTV = f(R.id.saveWriteTV);
        topicTitleEt = f(R.id.topicTitleEt);
        topicContentEt = f(R.id.topicContentEt);

        if (topic != null) {
            topicTitleEt.setText(topic.title);
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
                WriteOrEditTopicActivity.launch(topic, getApplicationContext());
                finish();
            }
        });

    }


}
