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

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.smart.elevator.R;
import com.smart.elevator.adapter.TaskAdapter;
import com.smart.elevator.bean.Task;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.data.DataFactory;
import com.smart.elevator.navi.LocationMgr;
import com.smart.elevator.navi.PoiSearchMgr;

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
    }

    public static TaskFragment getInstance() {
        return new TaskFragment();
    }

    public void initView(View view){
        mTaskListView = view.findViewById(R.id.task_list);
        mTask = DataFactory.getInstance(getContext()).mTasks;

        mTaskAdapter = new TaskAdapter(getContext(),mTask);
        mTaskListView.setAdapter(mTaskAdapter);
    };





}
