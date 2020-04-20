package com.smart.elevator.fragement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.smart.elevator.R;


public class AboutFragment extends Fragment {

    //个人信息
    TextView mUserID;
    TextView mUserName;
    TextView mUserSex;
    TextView mUserTel;
    Button mUpdateBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_about, container, false);
        initView(view);

        return view;
    }

    public static AboutFragment getInstance() {
        return new AboutFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){

    };

    public void initData() {

    }




}
