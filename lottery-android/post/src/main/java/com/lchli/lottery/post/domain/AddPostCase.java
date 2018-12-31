package com.lchli.lottery.post.domain;

import com.lchli.arch.clean.ResponseValue;
import com.lchli.arch.clean.UseCase;
import com.lchli.lottery.post.datainterface.PostSource;

/**
 * Created by lichenghang on 2018/12/31.
 */

public class AddPostCase extends UseCase<AddPostCase.Param, Void> {

    public interface Param {

    }

    private PostSource postSource;

    @Override
    protected ResponseValue<Void> execute(Param param) {
        return null;
    }
}
