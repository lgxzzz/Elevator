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
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.User;
import com.smart.elevator.data.DBManger;

import java.util.ArrayList;
import java.util.List;

/***
 * 人员编辑activity
 * 可浏览编辑信息和删除
 *
 * */
public class PersonOperateActivity extends AppCompatActivity{

    private EditText mNameEd;
    private EditText mPassWordEd;
    private EditText mRepeatPassWordEd;
    private EditText mTelEd;
    private EditText mMailEd;
    private TextView mRoleTv;
    private Spinner mRoleSp;
    private String mSelectRole="";


    private Button mUpdateBtn;
    private Button mDeleteBtn;

    boolean isEdit = false;
    private User mUser;
    String mOpt = "detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        init();
    }

    public void init(){
        mNameEd = findViewById(R.id.reg_name_ed);
        mPassWordEd = findViewById(R.id.reg_password_ed);
        mRepeatPassWordEd = findViewById(R.id.reg_repeat_password_ed);
        mTelEd = findViewById(R.id.reg_phone_ed);
        mMailEd = findViewById(R.id.reg_mail_ed);
        mRoleTv = findViewById(R.id.user_role_tv);
        mRoleSp = findViewById(R.id.user_role);
        mUpdateBtn = findViewById(R.id.elevator_update_btn);
        mDeleteBtn = findViewById(R.id.elevator_delete_btn);


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


        mOpt =  (String) getIntent().getExtras().getSerializable("opt");


        if (mOpt.equals("add")){
            mUser = new User();
            mRoleSp.setVisibility(View.VISIBLE);
            switchEdit(true);
            mRoleTv.setText(mSelectRole);
            mRoleTv.setVisibility(View.GONE);
            mRoleSp.setVisibility(View.VISIBLE);
            mUpdateBtn.setText("添加");
            mDeleteBtn.setText("取消");

            mUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUser();
                }
            });
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else{
            mUser =  (User) getIntent().getExtras().getSerializable("user");
            mNameEd.setText(mUser.getUserName());
            mPassWordEd.setText(mUser.getPassword());
            mTelEd.setText(mUser.getTelephone());
            mMailEd.setText(mUser.getMail());
            mRoleTv.setText(mUser.getRole());
            mRoleTv.setVisibility(View.GONE);
            mRoleSp.setVisibility(View.VISIBLE);
            mSelectRole=mUser.getRole();
            switchEdit(true);
            mUpdateBtn.setText("更新");
            mDeleteBtn.setText("删除");
            mUpdateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUser();
                }
            });
            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteUser();
                }
            });
        }
    }

    public void deleteUser(){
        Toast.makeText(PersonOperateActivity.this,"删除用户成功！",Toast.LENGTH_LONG).show();
        DBManger.getInstance(this).deleteUser(mUser);
        PersonOperateActivity.this.finish();
    }

    public void updateUser(){
        String mName = mNameEd.getEditableText().toString();
        String mPassWord = mPassWordEd.getEditableText().toString();
        String mTel = mTelEd.getEditableText().toString();
        String mMail = mMailEd.getEditableText().toString();

        if (mName==null || mName.length()==0){
            Toast.makeText(PersonOperateActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
            return;
        }
        if (mPassWord==null || mPassWord.length() ==0){
            Toast.makeText(PersonOperateActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
            return;
        }


        mUser.setRole(mSelectRole);
        mUser.setUserName(mName);
        mUser.setPassword(mPassWord);
        mUser.setTelephone(mTel);
        mUser.setMail(mMail);
        DBManger.getInstance(PersonOperateActivity.this).updateUser(mUser, new DBManger.IListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(PersonOperateActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                startActivity(new Intent(PersonOperateActivity.this, MainActivity.class));
                PersonOperateActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(PersonOperateActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void switchEdit(boolean isEdit){
        this.isEdit = isEdit;
        mNameEd.setEnabled(isEdit);
        mPassWordEd.setEnabled(isEdit);
        mTelEd.setEnabled(isEdit);
        mMailEd.setEnabled(isEdit);

    }
}
