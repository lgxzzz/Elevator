package com.smart.elevator.util;

import android.content.Context;
import android.content.Intent;

import com.smart.elevator.constant.Constant;

public class NotifyState {
    //通知更新数据
    public static void notifyRefreshData(Context mContext){
        Intent intent = new Intent();
        intent.setAction(Constant.INTENT_REFRESH_DATA);
        mContext.sendBroadcast(intent);
    }
}
