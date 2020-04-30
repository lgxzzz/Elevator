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
import android.widget.Button;
import android.widget.ListView;

import com.smart.elevator.PersonOperateActivity;
import com.smart.elevator.R;
import com.smart.elevator.adapter.UserAdapter;
import com.smart.elevator.bean.User;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;


public class PersonMgrFragment extends Fragment {

    List<User> mUsers = new ArrayList<>();

    ListView mListView;

    UserAdapter mAdapter;

    Button mAddBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_person, container, false);
        initView(view);
        registerBroadcast();
        return view;
    }

    public static PersonMgrFragment getInstance() {
        return new PersonMgrFragment();
    }

    public void initView(View view){
        mListView = view.findViewById(R.id.user_list);

        mAdapter = new UserAdapter(getContext());
        mListView.setAdapter(mAdapter);
        mAddBtn = view.findViewById(R.id.add_ele_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), PersonOperateActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("opt","add");
                intent.putExtras(b);
                getContext().startActivity(intent);
            }
        });
    };

    public void initData(){
        mUsers = DBManger.getInstance(getContext()).getAllUsers();
        mAdapter.setData(mUsers);
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
