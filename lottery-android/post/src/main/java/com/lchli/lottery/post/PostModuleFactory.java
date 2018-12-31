package com.lchli.lottery.post;

import com.lchli.lottery.post.datainterface.PostSource;

/**
 * Created by lichenghang on 2018/12/31.
 */

public interface PostModuleFactory {

    PostSource providePostSource();
}
