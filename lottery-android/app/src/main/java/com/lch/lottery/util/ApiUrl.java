package com.lch.lottery.util;

/**
 * Created by lichenghang on 2017/12/16.
 */

public final class ApiUrl {
    public static final int API_CODE_SUCCESS = 1;
    public static final String API_FIELD_STATUS = "Status";
    public static final String API_FIELD_MSG = "Message";

    private static final String DOMAIN = "http://180.76.118.73:8088/";
    public static final String REGISTER = DOMAIN + "user/register";
    public static final String LOGIN = DOMAIN + "user/login";
    public static final String USER_UPDATE = DOMAIN + "user/update";
    public static final String TOPIC_ADD = DOMAIN + "topic/add";
    public static final String TOPIC_GET = DOMAIN + "topic/get";
}
