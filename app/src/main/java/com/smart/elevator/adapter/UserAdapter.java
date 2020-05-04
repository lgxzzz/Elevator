package com.smart.elevator.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smart.elevator.PersonOperateActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.User;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends BaseAdapter {

    Context mContext;
    List<User> mList = new ArrayList<>();
    TaskDetailDialog mDialog;
    public UserAdapter(Context mContext){
        this.mContext = mContext;
        mDialog = new TaskDetailDialog(mContext,R.layout.dialog_task_detail,true,true);
    }

    public void setData(List<User> mList){
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
        final User user = this.mList.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.person_item,null);
            holer.mName = (TextView) view.findViewById(R.id.user_name);
            holer.mID = (TextView) view.findViewById(R.id.user_id);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mName.setText(user.getUserName());
        holer.mID.setText(user.getUserId());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, PersonOperateActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("user",user);
                b.putSerializable("opt","detail");
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mName;
        TextView mID;
    }

}
