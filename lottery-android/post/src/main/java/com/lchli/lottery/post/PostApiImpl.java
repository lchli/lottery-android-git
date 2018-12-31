package com.lchli.lottery.post;

import com.lchli.lottery.post.dataimpl.CloudPostSource;
import com.lchli.lottery.post.datainterface.PostSource;
import com.lchli.lottery.postapi.PostApi;

/**
 * Created by lichenghang on 2018/12/31.
 */

public class PostApiImpl implements PostApi {
    @Override
    public void init() {
        PostModuleInjector.getINS().initFactory(new PostModuleFactory() {
            @Override
            public PostSource providePostSource() {
                return new CloudPostSource();
            }
        });


    }
}
