package com.smart.elevator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.view.TitleView;


public class ElevotorParamsActivity extends AppCompatActivity {
    private TitleView mTitleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_params);

        init();
    }

    public void init() {
        mTitleView = findViewById(R.id.title_view);
        mTitleView.setTitle("电梯参数");
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Task mTask = (Task) getIntent().getExtras().getSerializable("task");

    }
}
