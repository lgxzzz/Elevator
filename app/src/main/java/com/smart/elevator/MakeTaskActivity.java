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

public class MakeTaskActivity extends AppCompatActivity {

    private EditText mNameEd;
    private EditText mPassWordEd;
    private EditText mRepeatPassWordEd;
    private EditText mTelEd;
    private EditText mMailEd;
    private Spinner mRoleSp;
    private Button mRegBtn;
    private String mSelectRole="";
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_task);

        init();
    }

    public void init(){
        mUser = new User();


        mNameEd = findViewById(R.id.reg_name_ed);
        mPassWordEd = findViewById(R.id.reg_password_ed);
        mRepeatPassWordEd = findViewById(R.id.reg_repeat_password_ed);
        mTelEd = findViewById(R.id.reg_phone_ed);
        mMailEd = findViewById(R.id.reg_mail_ed);
        mRegBtn = findViewById(R.id.reg_btn);
        mRoleSp = findViewById(R.id.user_role);

        final List<String> mRoles =new ArrayList<>();
        mRoles.add("维保人员");
        mRoles.add("维保接待员");
        mRoles.add("维保系统管理员");
        mRoles.add("报修人员");

        SpinnerAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mRoles);
        mRoleSp.setAdapter(adapter);
        mSelectRole = mRoles.get(0);

        mRoleSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectRole = mRoles.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mNameEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setUserName(editable.toString());
            }
        });

        mPassWordEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setPassword(editable.toString());
            }
        });

        mRepeatPassWordEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setRepeatPassword(editable.toString());
            }
        });

        mTelEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setTelephone(editable.toString());
            }
        });


        mMailEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setMail(editable.toString());
            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser.getUserName()==null){
                    Toast.makeText(MakeTaskActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getPassword()==null){
                    Toast.makeText(MakeTaskActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getRepeatPassword()==null){
                    Toast.makeText(MakeTaskActivity.this,"重复密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!mUser.getRepeatPassword().equals(mUser.getPassword())){
                    Toast.makeText(MakeTaskActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }
                mUser.setRole(mSelectRole);
                DBManger.getInstance(MakeTaskActivity.this).registerUser(mUser, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MakeTaskActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MakeTaskActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MakeTaskActivity.this,"注册失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
