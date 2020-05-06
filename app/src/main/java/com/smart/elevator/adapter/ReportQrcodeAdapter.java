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
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.view.ReportDialog;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;


/***
 * 报修人员显示的电梯列表适配器
 * 点击后显示二维码报修弹窗
 * */
public class ReportQrcodeAdapter extends BaseAdapter {

    Context mContext;
    List<Elevator> mList = new ArrayList<>();
    ReportDialog mDialog;
    public ReportQrcodeAdapter(Context mContext){
        this.mContext = mContext;
        mDialog = new ReportDialog(mContext,R.layout.dialog_report,true,true);
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
                mDialog.setData(elevator);
                mDialog.show();
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mEleID;
        TextView mEleAddress;
    }

}
