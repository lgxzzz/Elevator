package com.smart.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smart.elevator.R;
import com.smart.elevator.bean.Task;
import com.smart.elevator.view.TaskDetailDialog;

import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends BaseAdapter {

    Context mContext;
    List<Task> mTask = new ArrayList<>();

    public TaskAdapter(Context mContext, List<Task> mTask){
        this.mContext = mContext;
        this.mTask = mTask;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.task_item,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDetailDialog dialog = new TaskDetailDialog(mContext,R.layout.dialog_task_detail,true,true);
                dialog.show();
            }
        });
        return view;
    }
}
