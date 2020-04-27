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
import com.smart.elevator.adapter.SignAdapter;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;


public class PersonMgrFragment extends Fragment {

    List<Sign> mSign = new ArrayList<>();

    ListView mSignListView;

    SignAdapter mSignAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_sign, container, false);
        initView(view);
        registerBroadcast();
        return view;
    }

    public static PersonMgrFragment getInstance() {
        return new PersonMgrFragment();
    }

    public void initView(View view){
        mSignListView = view.findViewById(R.id.sign_listview);

        mSignAdapter = new SignAdapter(getContext());
        mSignListView.setAdapter(mSignAdapter);
    };

    public void initData(){
        mSign = DBManger.getInstance(getContext()).getAllRepairSign();
        mSignAdapter.setData(mSign);
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
