package com.smart.elevator.fragement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smart.elevator.R;
import com.smart.elevator.adapter.ElevatorAdapter;
import com.smart.elevator.adapter.SignAdapter;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;


public class ElevotarMgrFragment extends Fragment {

    List<Elevator> mElevators = new ArrayList<>();

    ListView mEleListView;

    ElevatorAdapter mEleAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_elevator, container, false);
        initView(view);
        registerBroadcast();
        return view;
    }

    public static ElevotarMgrFragment getInstance() {
        return new ElevotarMgrFragment();
    }

    public void initView(View view){
        mEleListView = view.findViewById(R.id.ele_list);

        mEleAdapter = new ElevatorAdapter(getContext());
        mEleListView.setAdapter(mEleAdapter);
    };

    public void initData(){
//        mSign = DBManger.getInstance(getContext()).getAllRepairSign();
//        mEleAdapter.setData(mSign);
    }



    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.INTENT_REFRESH_DATA);
        getContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constant.INTENT_REFRESH_DATA)){
                    initData();
                }
            }
        },filter);
    }
}
