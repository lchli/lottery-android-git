package com.lchli.lottery.postapi;

/**
 * Created by lichenghang on 2018/12/31.
 */

public class PostApiManager implements PostApi {
    private static final PostApiManager INS = new PostApiManager();

    public static PostApiManager getINS() {
        return INS;
    }

    private PostApi postApi;

    public void setPostApiImpl(PostApi postApi) {
        this.postApi = postApi;
    }

    @Override
    public void init() {
        postApi.init();
    }
}
