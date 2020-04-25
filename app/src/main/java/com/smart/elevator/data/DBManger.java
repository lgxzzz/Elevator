package com.smart.elevator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.smart.elevator.bean.ElevatorParams;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.util.NotifyState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBManger {
    private Context mContext;
    private SQLiteDbHelper mDBHelper;
    public User mUser;
    public DataFactory mDataFactory;
    public static  DBManger instance;

    public static DBManger getInstance(Context mContext){
        if (instance == null){
            instance = new DBManger(mContext);
        }
        return instance;
    };

    public DBManger(Context mContext){
        this.mContext = mContext;
        mDBHelper = new SQLiteDbHelper(mContext);
        mDataFactory = new DataFactory(mContext);
        mHandler.sendEmptyMessageDelayed(HSG_CHCEK_STATE,10*1000);
    }


    //用户登陆
    public void login(String name,String password,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME =? and USER_PASSWORD=?",new String[]{name,password});
            if (cursor.moveToFirst()){
                String USER_ID = cursor.getString(cursor.getColumnIndex("USER_ID"));
                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                String USER_MAIL = cursor.getString(cursor.getColumnIndex("USER_MAIL"));
                String LIFT_PROCESSORPHONE = cursor.getString(cursor.getColumnIndex("LIFT_PROCESSORPHONE"));

                mUser = new User();
                mUser.setUserId(USER_ID);
                mUser.setUserName(USER_NAME);
                mUser.setTelephone(LIFT_PROCESSORPHONE);
                mUser.setMail(USER_MAIL);
                listener.onSuccess();
            }else{
                listener.onError("未查询到该用户");
            }
            db.close();
            return;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        listener.onError("未查询到该用户");
    }

    //修改用户信息
    public void updateUser(User user,IListener listener){
        try{
            ContentValues values = new ContentValues();
            values.put("UserName",user.getUserName());
            values.put("Sex",user.getSex());
            values.put("Telephone",user.getTelephone());
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int code = db.update(SQLiteDbHelper.TAB_USER,values,"UserId =?",new String[]{user.getUserId()+""});
            listener.onSuccess();
        }catch (Exception e){

        }
    }

    //注册用户
    public void registerUser(User user,IListener listener){
        try{
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME=?",new String[]{user.getUserName()});
            if (cursor.moveToFirst()){
                listener.onError("用户名已经被注册！");
            }else{
                String userid = getRandomUSER_ID();
                ContentValues values = new ContentValues();
                values.put("USER_ID",userid);
                values.put("USER_NAME",user.getUserName());
                values.put("USER_PASSWORD",user.getPassword());
                values.put("LIFT_PROCESSORPHONE",user.getTelephone());
                values.put("USER_MAIL",user.getMail());
                values.put("USER_CHARCTER","人员");
                mUser = user;
                mUser.setUserId(userid);
                long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
                listener.onSuccess();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    };

    //生成随机userid
    public String getRandomUSER_ID(){
        String strRand="LF" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //更新维修签到
    public void insertRepairSign(Task task){
        //更新任务状态
        mDataFactory.updateTask(task);
        mDataFactory.addRepairSign(task);
    }

    //提交任务
    public void updateTaskFinish(Task task){
        mDataFactory.updateTask(task);
//        mDataFactory.mRepairSigns.remove(task.getLIFT_FORMID());
    }

    //获取所有任务
    public List<Task> getCurrentTasks(){
        List<Task> mtask = new ArrayList<>();
        for(Map.Entry<String, Task> entry: mDataFactory.mTasks.entrySet()){
            Task task = entry.getValue();
            mtask.add(task);
        }
        return mtask;
    }

    //根据eleid获取电梯参数
    public ElevatorParams getElevatorParamsByID(String LIFT_ID){
        ElevatorParams elevatorParams = mDataFactory.mElevatorParams.get(LIFT_ID);
        return elevatorParams;
    }

    //获取所有维修签到
    public List<Sign> getAllRepairSign(){
        List<Sign> mSigns= new ArrayList<>();
        for(Map.Entry<String, Sign> entry: mDataFactory.mRepairSigns.entrySet()){
            Sign sign = entry.getValue();
            mSigns.add(sign);
        }
        return mSigns;
    }

    //更新签到状态和任务状态
    public void updateSign(Sign sign){
        Task task = sign.getTask();
        mDataFactory.mRepairSigns.put(task.getLIFT_FORMID(),sign);
        mDataFactory.mTasks.put(task.getLIFT_FORMID(),task);

    }

    //轮训任务是否超时
    public static final int HSG_CHCEK_STATE = 1;
    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case HSG_CHCEK_STATE:
                    checkTaskState();
                    NotifyState.notifyRefreshData(mContext);
                    mHandler.sendEmptyMessageDelayed(HSG_CHCEK_STATE,10*1000);
                    break;
            }
            return false;
        }
    });

    public void checkTaskState(){
        for(Map.Entry<String, Task> entry: mDataFactory.mTasks.entrySet()){
            Task task = entry.getValue();
            long sendtime = getStringToDate(task.getLIFT_SENDTIME(),pattern);
            long nowtime = System.currentTimeMillis();
            long temp = nowtime - sendtime;
            if ((temp)>Constant.SIGN_TIME_OUT&&(task.getLIFT_CURRENTSTATE().equals("待接受")||task.getLIFT_CURRENTSTATE().equals("已接受待签到"))){
                task.setLIFT_CURRENTSTATE("已超时");
            }

        }
        for(Map.Entry<String, Sign> entry: mDataFactory.mRepairSigns.entrySet()){
            Sign sign = entry.getValue();
            Task task = sign.getTask();
            long sendtime = getStringToDate(task.getLIFT_SENDTIME(),pattern);
            long nowtime = System.currentTimeMillis();
            long temp = nowtime - sendtime;
            if ((temp)> Constant.SIGN_TIME_OUT&&sign.getState().equals("待签到")){
                sign.setState("已超时");
            }

        }
    }

    String pattern = "yyyy-MM-dd HH:mm:ss";
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public String getDateTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }


    public interface IListener{
        public void onSuccess();
        public void onError(String error);
    };


}
