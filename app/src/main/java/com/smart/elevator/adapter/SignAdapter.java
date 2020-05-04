package com.smart.elevator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.elevator.R;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.bean.Task;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.view.SignDialog;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;


public class SignAdapter extends BaseAdapter {

    Context mContext;
    List<Task> mSign = new ArrayList<>();
    SignDialog mDialog;
    public SignAdapter(Context mContext){
        this.mContext = mContext;
        mDialog = new SignDialog(mContext,R.layout.dialog_sign,true,true);
    }

    public void setData( List<Task> mSign){
        this.mSign = mSign;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSign.size();
    }

    @Override
    public Object getItem(int i) {
        return mSign.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Task task = this.mSign.get(i);
        SignAdapter.ViewHoler holer = null;
        if (view == null){
            holer = new SignAdapter.ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.sign_item,null);
            holer.mTime = (TextView) view.findViewById(R.id.sign_task_sendtime);
            holer.mAddress = (TextView) view.findViewById(R.id.sign_address);
            holer.mState = (TextView) view.findViewById(R.id.sign_state);
            view.setTag(holer);
        }else{
            holer = (SignAdapter.ViewHoler) view.getTag();
        }
        holer.mTime.setText(task.getLIFT_SENDTIME());
        holer.mAddress.setText("任务："+task.getElevator().getLIFT_USER());
        holer.mState.setText("状态："+task.getLIFT_CURRENTSTATE());
        if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING_SIGN)){
            holer.mState.setTextColor(Color.GREEN);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_SIGN)||task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_FINISH)){
            holer.mState.setTextColor(Color.BLUE);
        }else{
            holer.mState.setTextColor(Color.RED);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.setData(task);
                mDialog.show();
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mTime;
        TextView mAddress;
        TextView mState;
    }
}
