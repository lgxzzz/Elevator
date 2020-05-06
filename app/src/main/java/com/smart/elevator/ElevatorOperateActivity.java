package com.smart.elevator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.bean.Elevator;
import com.smart.elevator.data.DBManger;

/***
 * 电梯编辑操作activity
 *可编辑或删除
 * */
public class ElevatorOperateActivity extends Activity implements View.OnClickListener{

    private EditText LIFT_ID;
    private EditText LIFT_IDCODE;
    private EditText LIFT_USER;
    private EditText LIFT_AREAID;
    private EditText LIFT_ADDRESSID;
    private EditText LIFT_MAINTENANCENAME_ID;
    private EditText LIFT_BRANDID;
    private EditText LIFT_PRODUCT;
    private EditText LIFT_PRODUCTDATE;
    private EditText LIFT_STATUS;

    private Button mUpdateBtn;
    private Button mDeleteBtn;

    boolean isEdit = false;
    Elevator mElevator;
    String mOpt = "detail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_operate);

        init();
    }

    public void init(){



        LIFT_ID = findViewById(R.id.LIFT_ID);
        LIFT_IDCODE = findViewById(R.id.LIFT_IDCODE);
        LIFT_USER = findViewById(R.id.LIFT_USER);
        LIFT_AREAID = findViewById(R.id.LIFT_AREAID);
        LIFT_ADDRESSID = findViewById(R.id.LIFT_ADDRESSID);
        LIFT_MAINTENANCENAME_ID = findViewById(R.id.LIFT_MAINTENANCENAME_ID);
        LIFT_BRANDID = findViewById(R.id.LIFT_BRANDID);
        LIFT_PRODUCT = findViewById(R.id.LIFT_PRODUCT);
        LIFT_PRODUCTDATE = findViewById(R.id.LIFT_PRODUCTDATE);
        LIFT_STATUS = findViewById(R.id.LIFT_STATUS);



        mUpdateBtn = findViewById(R.id.elevator_update_btn);
        mDeleteBtn = findViewById(R.id.elevator_delete_btn);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);

        //传递过来的操作opt区分动作
        mOpt =  (String) getIntent().getExtras().getSerializable("opt");

        //添加电梯
        if (mOpt.equals("add")){
            mElevator = new Elevator();
            switchEdit(true);
        }else{
            //编辑电梯
            mElevator =  (Elevator) getIntent().getExtras().getSerializable("elevator");
            LIFT_ID.setText(mElevator.getLIFT_ID());
            LIFT_IDCODE.setText(mElevator.getLIFT_IDCODE());
            LIFT_USER.setText(mElevator.getLIFT_USER());
            LIFT_AREAID.setText(mElevator.getLIFT_AREAID());
            LIFT_ADDRESSID.setText(mElevator.getLIFT_ADDRESSID());
            LIFT_MAINTENANCENAME_ID.setText(mElevator.getLIFT_MAINTENANCENAME_ID());
            LIFT_BRANDID.setText(mElevator.getLIFT_BRANDID());
            LIFT_PRODUCT.setText(mElevator.getLIFT_PRODUCT());
            LIFT_PRODUCT.setText(mElevator.getLIFT_PRODUCT());
            LIFT_PRODUCTDATE.setText(mElevator.getLIFT_PRODUCTDATE());
            LIFT_STATUS.setText(mElevator.getLIFT_STATUS());
            switchEdit(false);
        }

    }

    //是否编辑切换控件状态
    public void switchEdit(boolean isEdit){
        this.isEdit = isEdit;
        LIFT_ID.setEnabled(isEdit);
        LIFT_IDCODE.setEnabled(isEdit);
        LIFT_USER.setEnabled(isEdit);
        LIFT_AREAID.setEnabled(isEdit);
        LIFT_ADDRESSID.setEnabled(isEdit);
        LIFT_MAINTENANCENAME_ID.setEnabled(isEdit);
        LIFT_BRANDID.setEnabled(isEdit);
        LIFT_PRODUCT.setEnabled(isEdit);
        LIFT_PRODUCTDATE.setEnabled(isEdit);
        LIFT_STATUS.setEnabled(isEdit);
        if (isEdit){
            mUpdateBtn.setText("确定");
            mDeleteBtn.setText("取消");
        }else{
            mUpdateBtn.setText("编辑");
            mDeleteBtn.setText("删除");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.elevator_update_btn:
                if (isEdit){
                    updateElevator();
                }else{
                    switchEdit(true);
                }
                break;
            case R.id.elevator_delete_btn:
                if (isEdit){
                    switchEdit(false);
                }else{
                    deleteElevator();
                }
                break;
        }
    }

    //删除电梯信息
    public void deleteElevator(){
        DBManger.getInstance(this).delteElevator(mElevator);
        finish();
    }

    //更新电梯信息
    public void updateElevator(){
        mElevator.setLIFT_ID(LIFT_ID.getEditableText().toString());
        mElevator.setLIFT_IDCODE(LIFT_IDCODE.getEditableText().toString());
        mElevator.setLIFT_USER(LIFT_USER.getEditableText().toString());
        mElevator.setLIFT_AREAID(LIFT_AREAID.getEditableText().toString());
        mElevator.setLIFT_ADDRESSID(LIFT_ADDRESSID.getEditableText().toString());
        mElevator.setLIFT_MAINTENANCENAME_ID(LIFT_MAINTENANCENAME_ID.getEditableText().toString());
        mElevator.setLIFT_BRANDID(LIFT_BRANDID.getEditableText().toString());
        mElevator.setLIFT_PRODUCT(LIFT_PRODUCT.getEditableText().toString());
        mElevator.setLIFT_PRODUCTDATE(LIFT_PRODUCTDATE.getEditableText().toString());
        mElevator.setLIFT_STATUS(LIFT_STATUS.getEditableText().toString());
        DBManger.getInstance(this).updateElevator(mElevator);
    }
}
