package com.smart.elevator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.smart.elevator.bean.User;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;


public class PlanTaskActivity extends AppCompatActivity {
    private Spinner mPersonSp;
    private Spinner mElevatorSp;
    private EditText mTimeEd;
    private Button mSureBtn;
    private Button mCancelBtn;

    List<String> mPersonData =new ArrayList<>();
    List<String> mEleData =new ArrayList<>();

    private String mSelectPerson="";
    private String mSelectEle="";
    private int mSelectTime = 1;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_task);

        init();
    }

    public void init() {

        mPersonSp = findViewById(R.id.select_person_sp);
        mElevatorSp = findViewById(R.id.select_ele_sp);
        mTimeEd =findViewById(R.id.select_time);
        mSureBtn = findViewById(R.id.sure_btn);
        mCancelBtn = findViewById(R.id.cancel_btn);

        mPersonData = DBManger.getInstance(this).getUsersNameByRole("维保人员");

        SpinnerAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mPersonData);
        mPersonSp.setAdapter(adapter);
        mSelectPerson = mPersonData.get(0);

        mPersonSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectPerson = mPersonData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEleData = DBManger.getInstance(this).getAllElevatorID();

        SpinnerAdapter adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mEleData);
        mElevatorSp.setAdapter(adapter1);
        mSelectEle = mEleData.get(0);

        mPersonSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectEle = mEleData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
