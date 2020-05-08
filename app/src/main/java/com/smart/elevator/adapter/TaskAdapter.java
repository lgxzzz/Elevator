package com.smart.elevator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.elevator.R;
import com.smart.elevator.bean.Task;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;

/***
 * 维保人员和接待员的任务列表适配器
 * 点击后显示任务弹窗，可以选择接受任务，提交任务动作
 * */
public class TaskAdapter extends BaseAdapter {

    Context mContext;
    List<Task> mTask = new ArrayList<>();
    TaskDetailDialog mDialog;
    public TaskAdapter(Context mContext){
        this.mContext = mContext;

    }

    public void setData(List<Task> mTask){
        this.mTask = mTask;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTask.size();
    }

    @Override
    public Object getItem(int i) {
        return mTask.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Task task = this.mTask.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.task_item,null);
            holer.mTime = (TextView) view.findViewById(R.id.task_time);
            holer.mAddress = (TextView) view.findViewById(R.id.task_add);
            holer.mState = (TextView) view.findViewById(R.id.task_state);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }

        holer.mTime.setText(task.getLIFT_SENDTIME());
        holer.mAddress.setText(task.getFORM_STATE()+"任务："+task.getElevator().getLIFT_USER());
        holer.mState.setText("状态："+task.getLIFT_CURRENTSTATE());
        if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING)){
            holer.mState.setTextColor(Color.GREEN);
        } else  if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING_SIGN)){
            holer.mState.setTextColor(Color.BLUE);
        } else  if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_SIGN)){
            holer.mState.setTextColor(Color.BLUE);
        } else  if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_FINISH)){
            holer.mState.setTextColor(Color.BLACK);
        } else{
            holer.mState.setTextColor(Color.RED);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new TaskDetailDialog(mContext,R.layout.dialog_task_detail,true,true);
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
