package com.lch.lottery.topic.model;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.lch.lottery.common.BaseResponse;
import com.lch.lottery.common.VerticalScrollTextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class NoticeResponse extends BaseResponse {

    public List<Notice> data;

    public static class Notice implements Serializable, VerticalScrollTextView.Data {
        public String text;
        public String linkUrl;

        @Override
        public void bindView(TextView view) {
            view.setText(text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(linkUrl);
                }
            });
        }
    }
}
