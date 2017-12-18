package com.lch.lottery.topic;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by bbt-team on 2017/12/15.
 */

public class TopicModel implements Serializable {
    @JSONField(name = "TopicId")
    public String topicId;
    @JSONField(name = "UserId")
    public String userId;
    @JSONField(name = "UserName")
    public String userName;
    @JSONField(name = "TopicTitle")
    public String topicTitle;
    @JSONField(name = "TopicContent")
    public String topicContent;
    @JSONField(name = "TopicTag")
    public String topicTag;
    @JSONField(name = "LastModifyTime")
    public String lastModifyTime;
}
