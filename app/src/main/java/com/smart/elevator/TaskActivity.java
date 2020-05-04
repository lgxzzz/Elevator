package com.smart.elevator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.adapter.TaskAdapter;
import com.smart.elevator.bean.Task;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.view.TitleView;

import java.util.ArrayList;
import java.util.List;


public class TaskActivity extends Activity{

    private TitleView mTitleView;
    List<Task> mTask = new ArrayList<>();

    ListView mTaskListView;

    TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        init();
    }

    public void init(){
        String state = (String) getIntent().getExtras().getSerializable("state");
        mTitleView = findViewById(R.id.title_view);
        mTitleView.setTitle(state);
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTaskListView = findViewById(R.id.task_listview);
        mTaskAdapter = new TaskAdapter(this);
        mTaskListView.setAdapter(mTaskAdapter);
        List<Task> tempTasks = DBManger.getInstance(this).getAllTasks();
        filterTaskByState(state,tempTasks);
        mTaskAdapter.setData(mTask);
    }

    public void filterTaskByState(String state,List<Task> tasks){
        for (int i=0;i<tasks.size();i++){
            Task task = tasks.get(i);
            if (state.equals("历史任务")){
                if (task.getLIFT_CURRENTSTATE().equals("已超时")||task.getLIFT_CURRENTSTATE().equals("已完成")){
                    mTask.add(task);
                }
            }else if(state.equals("当前任务")){
                if (task.getLIFT_CURRENTSTATE().equals("已接受待签到")||task.getLIFT_CURRENTSTATE().equals("已签到")){
                    mTask.add(task);
                }
            }
        }

    }

}
