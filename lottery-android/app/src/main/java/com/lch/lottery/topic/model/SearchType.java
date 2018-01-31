package com.lch.lottery.topic.model;

/**
 * Created by Administrator on 2018/1/31.
 */

public enum SearchType {
    TITLE,
    TAG;

    @Override
    public String toString() {
        switch (this) {
            case TITLE:
                return "标题";
            case TAG:
                return "标签";
        }
        return null;
    }
}
