package com.smart.elevator.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME = "Elevator.db";
    //数据库版本号
    public static int DB_VERSION = 6;
    //用户表
    public static final String TAB_USER = "UserInfo";
    //电梯表
    public static final String TAB_ELEVATOR = "Elevator";
    //电梯参数表
    public static final String TAB_ELEVATOR_PARAMS = "ElevatorParams";
    //维护保修工单表
    public static final String TAB_TASK = "Task";
    //签到表
    public static final String TAB_SIGN = "Sign";

    public SQLiteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createTableElevator(db);
        createTableElevatorParams(db);
        createTableTask(db);
        createTableSign(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TAB_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_ELEVATOR);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_ELEVATOR_PARAMS);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_TASK);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_SIGN);
        onCreate(db);
    }

    //创建人员表
    public void createTableUser(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_USER +
                "(USER_ID varchar(20) primary key, " +
                "USER_NAME varchar(20), " +
                "USER_PASSWORD varchar(20), " +
                "LIFT_PROCESSORPHONE varchar(20), " +
                "USER_MAIL varchar(20), " +
                "USER_CHARCTER varchar(20))");
    }

    //创建电梯信息表
    public void createTableElevator(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_USER +
                "(LIFT_ID varchar(20) primary key, " +
                "LIFT_IDCODE varchar(20), " +
                "LIFT_USER varchar(20), " +
                "LIFT_AREAID varchar(20), " +
                "LIFT_ADDRESSID varchar(20), " +
                "LIFT_MAINTENANCENAME_ID varchar(20), " +
                "LIFT_BRANDID varchar(20), " +
                "LIFT_PRODUCT varchar(20), " +
                "LIFT_PRODUCTDATE varchar(20), " +
                "LIFT_STATUS varchar(20))");
    }

    //创建电梯参数表
    public void createTableElevatorParams(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_ELEVATOR +
                "(LIFT_ID varchar(20) primary key, " +
                "LIFT_RATEDLOAD varchar(20), " +
                "LIFT_RATEDSPEED varchar(20), " +
                "LIFT_WIDTH varchar(20), " +
                "LIFT_HEIGHT varchar(20), " +
                "LIFT_VOLTAGE varchar(20), " +
                "LIFT_CURRENT varchar(20), " +
                "LIFT_TRACTORMODEL varchar(20), " +
                "LIFT_TRACTIORWHEELDIAMETER varchar(20), " +
                "LIFT_TRACTIORRATIO varchar(20), " +
                "LIFT_TRACTIORTYPE varchar(20), " +
                "LIFT_BUFFERTYPE varchar(20), " +
                "LIFT_SAFETYGEARTYPE varchar(20), " +
                "LIFT_TRACTIORNUMBER varchar(20), " +
                "LIFT_TRACTIORROPENUMBER varchar(20), " +
                "LIFT_MOTORTYPE varchar(20))");
    }

    //创建维保工单表
    public void createTableTask(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_TASK +
                "(LIFT_FORMID varchar(20) primary key, " +
                "LIFT_ID varchar(20), " +
                "LIFT_PROCESSOR varchar(20), " +
                "LIFT_FAIULTTIME varchar(20), " +
                "LIFT_SENDTIME varchar(20), " +
                "LIFT_PROCESSORPHONE varchar(20), " +
                "LIFT_CURRENTSTATE varchar(20), " +
                "LIFT_FAULTTYPE varchar(20))");
    }

    //创建签到表
    public void createTableSign(SQLiteDatabase db){
//        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_SIGN +
//                "(BudegetTypeId varchar(60) primary key, " +
//                "type varchar(60), " +
//                "note varchar(60))");
    }
}
