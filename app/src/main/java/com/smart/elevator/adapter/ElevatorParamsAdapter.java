package com.smart.elevator.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.elevator.ElevatorOperateActivity;
import com.smart.elevator.ElevatorParamsOperateActivity;
import com.smart.elevator.ElevotorParamsActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.ElevatorParams;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;

/***
 * 电梯参数适配器
 * */
public class ElevatorParamsAdapter extends BaseAdapter {

    Context mContext;
    List<ElevatorParams> mList = new ArrayList<>();
    TaskDetailDialog mDialog;
    public ElevatorParamsAdapter(Context mContext){
        this.mContext = mContext;
        mDialog = new TaskDetailDialog(mContext,R.layout.dialog_task_detail,true,true);
    }

    public void setData(List<ElevatorParams> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ElevatorParams elevatorParams = this.mList.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.elevator_item,null);
            holer.mEleID = (TextView) view.findViewById(R.id.ele_id);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mEleID.setText(elevatorParams.getLIFT_ID());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ElevatorParamsOperateActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("elevatorParams",elevatorParams);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mEleID;
    }

}
