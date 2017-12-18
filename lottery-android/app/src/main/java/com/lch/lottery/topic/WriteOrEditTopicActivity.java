package com.lch.lottery.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lch.lottery.R;
import com.lch.netkit.common.base.BaseCompatActivity;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class WriteOrEditTopicActivity extends BaseCompatActivity {

    private TextView exitWriteTV;
    private TextView saveWriteTV;
    private EditText topicTitleEt;
    private EditText topicTagEt;
    private EditText topicContentEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_or_edit_topic);
        exitWriteTV = f(R.id.exitWriteTV);
        saveWriteTV = f(R.id.saveWriteTV);
        topicTitleEt = f(R.id.topicTitleEt);
        topicTagEt = f(R.id.topicTagEt);
        topicContentEt = f(R.id.topicContentEt);

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

            }
        });
    }


}
