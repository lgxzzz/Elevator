package com.smart.elevator.fragement;

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
import com.smart.elevator.adapter.TaskAdapter;
import com.smart.elevator.bean.Sign;
import com.smart.elevator.bean.Task;

import java.util.ArrayList;
import java.util.List;


public class SignFragment extends Fragment {

    List<Sign> mSign = new ArrayList<>();

    ListView mSignListView;

    SignAdapter mSignAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_sign, container, false);
        initView(view);

        return view;
    }

    public static SignFragment getInstance() {
        return new SignFragment();
    }

    public void initView(View view){
        mSignListView = view.findViewById(R.id.sign_listview);
        Sign sign = new Sign();
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);
        mSign.add(sign);

        mSignAdapter = new SignAdapter(getContext(),mSign);
        mSignListView.setAdapter(mSignAdapter);
    };

    public void initData(){

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
