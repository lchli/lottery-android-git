package com.lch.lottery.util;

/**
 * Created by lichenghang on 2017/12/16.
 */

public final class ApiUrl {
    public static final int API_CODE_SUCCESS = 1;

    private static final String DOMAIN = "http://192.168.1.103:8088/";
    public static final String REGISTER = DOMAIN + "user/register";
    public static final String LOGIN = DOMAIN + "user/login";
    public static final String USER_UPDATE = DOMAIN + "user/update";
    public static final String TOPIC_ADD = DOMAIN + "topic/add";
    public static final String TOPIC_GET = DOMAIN + "topic/get";
    public static final String FILE_UPLOAD = DOMAIN + "file/upload";
}
