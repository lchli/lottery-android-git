package com.lch.lottery.topic.model;

import android.text.TextUtils;

import com.lch.lottery.common.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lichenghang on 2017/12/16.
 */

public class TopicResponse extends BaseResponse {

    public List<Topic> data;

    public static class Topic implements Serializable {
        public String uid;
        public String title;
        public String tag;
        public String content;
        public String userId;
        public long updateTime;
        public String userName;

        public String tagText() {
            return TextUtils.isEmpty(tag) ? "默认" : tag;
        }
    }
}
