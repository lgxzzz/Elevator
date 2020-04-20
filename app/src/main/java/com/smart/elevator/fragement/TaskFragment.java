package com.smart.elevator.fragement;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.R;
import com.smart.elevator.adapter.TaskAdapter;
import com.smart.elevator.bean.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskFragment extends Fragment {

    List<Task> mTask = new ArrayList<>();

    ListView mTaskListView;

    TaskAdapter mTaskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_task, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initAllData();
    }

    public static TaskFragment getInstance() {
        return new TaskFragment();
    }

    public void initView(View view){
        mTaskListView = view.findViewById(R.id.task_list);
        Task task = new Task();
        mTask.add(task);
        mTask.add(task);
        mTask.add(task);
        mTask.add(task);
        mTask.add(task);

        mTaskAdapter = new TaskAdapter(getContext(),mTask);
        mTaskListView.setAdapter(mTaskAdapter);
    };


    public void initAllData(){

    };

}
