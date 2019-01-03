package com.lch.lottery.topic.domain;

import android.text.TextUtils;

import com.lch.lottery.topic.datainterface.TopicRepo;
import com.lch.lottery.topic.model.AdResponse;
import com.lch.lottery.topic.model.TopicResponse;
import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;

import java.util.List;

/**
 * Created by lichenghang on 2019/1/1.
 */

public class AddTopicCase extends UseCase<AddTopicCase.Param, Void> {

    public static class Param {
        public String title;
        public String tag;
        public String content;
        public String topicId;
    }

    private TopicRepo repo;

    public AddTopicCase(TopicRepo repo) {
        this.repo = repo;
    }

    @Override
    protected ResponseValue<Void> execute(AddTopicCase.Param param) {
        ResponseValue<Void> ret = new ResponseValue<>();
        if (TextUtils.isEmpty(param.title) || TextUtils.isEmpty(param.tag) || TextUtils.isEmpty(param.content)) {
            ret.setErrorMsg("参数不合法！");
            return ret;
        }

        TopicResponse.Topic topic = new TopicResponse.Topic();
        topic.uid = param.topicId;
        topic.title = param.title;
        topic.tag = param.tag;
        topic.content = param.content;
        topic.userId = "";//todo

        return repo.saveTopic(topic);
    }
}
