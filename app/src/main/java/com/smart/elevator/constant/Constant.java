package com.smart.elevator.constant;

public class Constant {
    public static final String INTENT_REFRESH_DATA = "com.smart.elevator.refreshdata";

    //签到超时时间
    public static long SIGN_TIME_OUT = 60*1000;

    //完成超时时间
    public static long FINISH_TIME_OUT = 10*1000;

    public static String TASK_STATE_REPORT = "已报修";
    public static String TASK_STATE_WAITING = "待接受";
    public static String TASK_STATE_WAITING_SIGN = "已接受待签到";
    public static String TASK_STATE_SIGN = "已签到";
    public static String TASK_STATE_FINISH = "已完成";
    public static String TASK_STATE_TIMEOUT = "已超时";
}
