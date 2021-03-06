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

import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;

/***
 *
 * 定期维修计划activity
 *
 * */
public class PlanTaskActivity extends AppCompatActivity {
    private Spinner mPersonSp;
    private Spinner mElevatorSp;
    private EditText mTimeEd;
    private Button mSureBtn;
    private Button mCancelBtn;

    List<String> mPersonData =new ArrayList<>();
    List<String> mEleData =new ArrayList<>();
    List<User> mUserData =new ArrayList<>();

    private String mSelectEle="";
    private String mSelectTime = "1";
    private User mSelectProcesPerson;

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
        //根据角色查询用户，这里把所有维保人员查询出来
        mUserData = DBManger.getInstance(this).getUsersNameByRole("维保人员");
        mPersonData.clear();
        for (int i = 0;i<mUserData.size();i++){
            String name = mUserData.get(i).getUserName();
            mPersonData.add(name);
        }
        mSelectProcesPerson = mUserData.get(0);
        SpinnerAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mPersonData);
        mPersonSp.setAdapter(adapter);

        mPersonSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectProcesPerson = mUserData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //查询所有电梯的id
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

        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String period = mTimeEd.getEditableText().toString();
                if (period.length()==0){
                    Toast.makeText(getBaseContext(),"请填入定期天数！",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(PlanTaskActivity.this,"添加定期任务成功！",Toast.LENGTH_LONG).show();
                mSelectTime = period;
                //创建定期维护计划，保存到数据
                DBManger.getInstance(PlanTaskActivity.this).createPlanTask(mSelectEle,mSelectProcesPerson,mSelectTime);
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
