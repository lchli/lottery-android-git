package com.lch.lottery.topic.ui;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.App;
import com.lch.lottery.R;
import com.lch.lottery.common.BottomSheetDialog;
import com.lch.lottery.topic.model.TopicResponse;
import com.lch.lottery.topic.vm.WriteTopicVm;
import com.lch.lottery.util.DialogUtil;
import com.lchli.utils.base.BaseCompatActivity;
import com.lchli.utils.tool.VF;


/**
 * Created by lichenghang on 2017/12/16.
 */

public class WriteOrEditTopicActivity extends BaseCompatActivity {

    private static final String INTENT_KEY_TOPIC = "topic";

    private TextView exitWriteTV;
    private TextView saveWriteTV;
    private EditText topicTitleEt;
    private TextView topicTagEt;
    private EditText topicContentEt;
    private TopicResponse.Topic topic;
    private Dialog loadingDialog;
    private WriteTopicVm writeTopicVm = new WriteTopicVm();

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
        topicTagEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTagDialog();
            }
        });

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

                writeTopicVm.onSaveTopic(title, tag, content, topic != null ? topic.uid : null);
            }
        });
        writeTopicVm.closeUiAction.observeForever(new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                finish();
            }
        });
        writeTopicVm.loadingViewState.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    if (loadingDialog == null) {
                        loadingDialog = DialogUtil.showLoadingDialog(WriteOrEditTopicActivity.this);
                    }
                } else {
                    if (loadingDialog != null) {
                        loadingDialog.dismiss();
                        loadingDialog = null;
                    }
                }
            }
        });
        writeTopicVm.toastState.observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ToastUtils.showShort(s);
            }
        });

    }

    private void showTagDialog() {
        final BottomSheetDialog mDialog = new BottomSheetDialog(this);
        View root = View.inflate(this, R.layout.choose_topic_tag_dialog, null);
        final TextView tvDanma = VF.f(root, R.id.tvDanma);
        final TextView tvShama = VF.f(root, R.id.tvShama);
        final TextView tvHewei = VF.f(root, R.id.tvHewei);
        final TextView tvKuadu = VF.f(root, R.id.tvKuadu);
        final TextView tvErma = VF.f(root, R.id.tvErma);
        final TextView tvDadi = VF.f(root, R.id.tvDadi);

        tvDanma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());

                mDialog.dismiss();
            }
        });
        tvShama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());
                mDialog.dismiss();
            }
        });
        tvHewei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());
                mDialog.dismiss();
            }
        });

        tvKuadu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());
                mDialog.dismiss();
            }
        });

        tvErma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());
                mDialog.dismiss();
            }
        });
        tvDadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicTagEt.setText(((TextView) v).getText());
                mDialog.dismiss();
            }
        });

        mDialog.contentView(root)
                .heightParam(ViewGroup.LayoutParams.WRAP_CONTENT)
                .inDuration(200)
                .outDuration(200)
                .cancelable(true)
                .show();
    }


    @Override
    protected void onDestroy() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        super.onDestroy();
    }


}
