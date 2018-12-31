package com.lchli.lottery.post.dataimpl;

import com.lchli.arch.clean.ResponseValue;
import com.lchli.lottery.post.datainterface.PostSource;
import com.lchli.lottery.post.model.Post;

/**
 * Created by lichenghang on 2018/12/31.
 */

public class CloudPostSource implements PostSource{
    @Override
    public ResponseValue<Post> query(QueryParam param) {
        return null;
    }

    @Override
    public ResponseValue<Void> add(Post post) {
        return null;
    }

    @Override
    public ResponseValue<Void> update(Post post) {
        return null;
    }
}
