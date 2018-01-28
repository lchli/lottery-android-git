package com.lch.lottery;

/**
 * Created by lichenghang on 2017/12/16.
 */

public final class ApiUrl {
    public static final int API_CODE_SUCCESS = 1;
    public static final String API_FIELD_STATUS = "Status";
    public static final String API_FIELD_MSG = "Message";

    private static final String DOMAIN = "http://192.168.1.102:8080/";
    public static final String REGISTER = DOMAIN + "user";
    public static final String LOGIN = DOMAIN + "user";
    public static final String TOPIC = DOMAIN + "topic";
}