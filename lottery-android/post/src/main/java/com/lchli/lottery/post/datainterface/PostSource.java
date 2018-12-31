package com.lchli.lottery.post.datainterface;

import com.lchli.arch.clean.ResponseValue;
import com.lchli.lottery.post.model.Post;

/**
 * Created by lichenghang on 2018/12/31.
 */

public interface PostSource {

    public interface QueryParam {

    }

    ResponseValue<Post> query(QueryParam param);

    ResponseValue<Void> add(Post post);

    ResponseValue<Void> update(Post post);
}
