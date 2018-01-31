package com.lch.lottery.topic.model;

/**
 * Created by Administrator on 2018/1/31.
 */

public enum TopicSorter {

    TIME_ASC,
    TIME_DESC;

    public String sortField() {
        switch (this) {
            case TIME_ASC:
            case TIME_DESC:
                return "updateTime";
        }
        return null;
    }

    public String sortDirec() {
        switch (this) {
            case TIME_ASC:
                return "asc";
            case TIME_DESC:
                return "desc";
        }
        return null;
    }

    @Override
    public String toString() {
        switch (this) {
            case TIME_ASC:
                return "时间升";
            case TIME_DESC:
                return "时间降";
        }
        return null;
    }
}
