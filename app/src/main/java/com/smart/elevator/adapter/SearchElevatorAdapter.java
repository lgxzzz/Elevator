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
import com.smart.elevator.ElevatorPlaceActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;

/***
 * 维保人员搜索电梯列表适配器
 * 点击后跳转地图界面显示具体位置
 * */
public class SearchElevatorAdapter extends BaseAdapter {

    Context mContext;
    List<Elevator> mList = new ArrayList<>();
    TaskDetailDialog mDialog;
    public SearchElevatorAdapter(Context mContext){
        this.mContext = mContext;
        mDialog = new TaskDetailDialog(mContext,R.layout.dialog_task_detail,true,true);
    }

    public void setData(List<Elevator> mList){
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
        final Elevator elevator = this.mList.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.elevator_item,null);
            holer.mEleID = (TextView) view.findViewById(R.id.ele_id);
            holer.mEleAddress = (TextView) view.findViewById(R.id.ele_address);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mEleID.setText(elevator.getLIFT_ID());
        holer.mEleAddress.setText(elevator.getLIFT_USER());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, ElevatorPlaceActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("elevator",elevator);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mEleID;
        TextView mEleAddress;
    }

}
