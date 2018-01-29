package com.lch.lottery.util;

/**
 * Created by lichenghang on 2018/1/29.
 */

public final class StringUtil {

    public static String ifNullToEmpty(String input) {
        return input == null ? "" : input;
    }
}
