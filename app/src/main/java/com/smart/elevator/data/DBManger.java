package com.smart.elevator.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.smart.elevator.bean.Sign;
import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DBManger {
    private Context mContext;
    private SQLiteDbHelper mDBHelper;
    public User mUser;
    private DataFactory mDataFactory;
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
    }


    //用户登陆
    public void login(String name,String password,IListener listener){
        try{
//            SQLiteDatabase db = mDBHelper.getWritableDatabase();
//            Cursor cursor = db.rawQuery("select * from UserInfo where USER_NAME =? and USER_PASSWORD=?",new String[]{name,password});
//            if (cursor.moveToFirst()){
//                String USER_NAME = cursor.getString(cursor.getColumnIndex("USER_NAME"));
//                String USER_PASSWORD = cursor.getString(cursor.getColumnIndex("USER_PASSWORD"));
//
//                mUser = new User();
//                mUser.setUserId(UserId);
//                mUser.setUserName(UserName);
                listener.onSuccess();
//            }else{
//                listener.onError("未查询到该用户");
//            }
//            db.close();
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
            ContentValues values = new ContentValues();
            values.put("UserName",user.getUserName());
            values.put("Password",user.getPassword());
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            long code = db.insert(SQLiteDbHelper.TAB_USER,null,values);
            listener.onSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }

    };

    //更新维修签到
    public void insertRepairSign(Task task){
        //更新任务状态
        mDataFactory.updateTask(task);
        mDataFactory.addRepairSign(task);
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


    //生成默认的电梯信息和电梯参数

    public interface IListener{
        public void onSuccess();
        public void onError(String error);
    };


}
