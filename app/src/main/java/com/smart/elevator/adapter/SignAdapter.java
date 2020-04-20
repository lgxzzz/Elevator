package com.smart.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smart.elevator.R;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.bean.Task;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;


public class SignAdapter extends BaseAdapter {

    Context mContext;
    List<Sign> mSign = new ArrayList<>();

    public SignAdapter(Context mContext, List<Sign> mSign){
        this.mContext = mContext;
        this.mSign = mSign;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.task_item,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
