package com.lchli.lottery.post;

import com.lchli.lottery.post.datainterface.PostSource;

/**
 * Created by lichenghang on 2018/12/31.
 */

public final class PostModuleInjector implements PostModuleFactory {
    private static final PostModuleInjector INS = new PostModuleInjector();
    private PostModuleFactory factory;

    public static PostModuleInjector getINS() {
        return INS;
    }

    public void initFactory(PostModuleFactory factory) {
        this.factory = factory;
    }

    @Override
    public PostSource providePostSource() {
        return factory.providePostSource();
    }
}
