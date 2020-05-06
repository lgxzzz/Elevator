package com.smart.elevator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.ElevatorParams;
import com.smart.elevator.data.DBManger;

/***
 * 电梯信息编辑activity
 * 可浏览编辑或删除
 *
 * */
public class ElevatorParamsOperateActivity extends Activity implements View.OnClickListener{

    EditText LIFT_ID;
    EditText LIFT_RATEDLOAD;
    EditText LIFT_RATEDSPEED;
    EditText LIFT_WIDTH;
    EditText LIFT_HEIGHT;
    EditText LIFT_VOLTAGE;
    EditText LIFT_CURRENT;
    EditText LIFT_TRACTORMODEL;
    EditText LIFT_TRACTIORWHEELDIAMETER;
    EditText LIFT_TRACTIORRATIO;
    EditText LIFT_TRACTIORTYPE;
    EditText LIFT_BUFFERTYPE;
    EditText LIFT_SAFETYGEARTYPE;
    EditText LIFT_TRACTIORNUMBER;
    EditText LIFT_TRACTIORROPENUMBER;
    EditText LIFT_MOTORTYPE;

    private Button mUpdateBtn;
    private Button mDeleteBtn;

    boolean isEdit = false;
    ElevatorParams mElevatorParams;
    String mOpt = "detail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_params_operate);

        init();
    }

    public void init(){
        mElevatorParams =  (ElevatorParams) getIntent().getExtras().getSerializable("elevatorParams");

        LIFT_ID = findViewById(R.id.LIFT_ID);
        LIFT_RATEDLOAD = findViewById(R.id.LIFT_RATEDLOAD);
        LIFT_RATEDSPEED = findViewById(R.id.LIFT_RATEDSPEED);
        LIFT_WIDTH = findViewById(R.id.LIFT_WIDTH);
        LIFT_HEIGHT = findViewById(R.id.LIFT_HEIGHT);
        LIFT_VOLTAGE = findViewById(R.id.LIFT_VOLTAGE);
        LIFT_CURRENT = findViewById(R.id.LIFT_CURRENT);
        LIFT_TRACTORMODEL = findViewById(R.id.LIFT_TRACTORMODEL);
        LIFT_TRACTIORWHEELDIAMETER = findViewById(R.id.LIFT_TRACTIORWHEELDIAMETER);
        LIFT_TRACTIORRATIO = findViewById(R.id.LIFT_TRACTIORRATIO);
        LIFT_TRACTIORTYPE = findViewById(R.id.LIFT_TRACTIORTYPE);
        LIFT_BUFFERTYPE = findViewById(R.id.LIFT_BUFFERTYPE);
        LIFT_SAFETYGEARTYPE = findViewById(R.id.LIFT_SAFETYGEARTYPE);
        LIFT_TRACTIORNUMBER = findViewById(R.id.LIFT_TRACTIORNUMBER);
        LIFT_TRACTIORROPENUMBER = findViewById(R.id.LIFT_TRACTIORROPENUMBER);
        LIFT_MOTORTYPE = findViewById(R.id.LIFT_MOTORTYPE);

        LIFT_ID.setText(mElevatorParams.getLIFT_ID());
        LIFT_RATEDLOAD.setText(mElevatorParams.getLIFT_RATEDLOAD());
        LIFT_RATEDSPEED.setText(mElevatorParams.getLIFT_RATEDSPEED());
        LIFT_WIDTH.setText(mElevatorParams.getLIFT_WIDTH());
        LIFT_HEIGHT.setText(mElevatorParams.getLIFT_HEIGHT());
        LIFT_VOLTAGE.setText(mElevatorParams.getLIFT_VOLTAGE());
        LIFT_CURRENT.setText(mElevatorParams.getLIFT_CURRENT());
        LIFT_TRACTORMODEL.setText(mElevatorParams.getLIFT_TRACTORMODEL());
        LIFT_TRACTIORWHEELDIAMETER.setText(mElevatorParams.getLIFT_TRACTIORWHEELDIAMETER());
        LIFT_TRACTIORRATIO.setText(mElevatorParams.getLIFT_TRACTIORRATIO());
        LIFT_TRACTIORTYPE.setText(mElevatorParams.getLIFT_TRACTIORTYPE());
        LIFT_SAFETYGEARTYPE.setText(mElevatorParams.getLIFT_SAFETYGEARTYPE());
        LIFT_TRACTIORNUMBER.setText(mElevatorParams.getLIFT_TRACTIORNUMBER());
        LIFT_TRACTIORROPENUMBER.setText(mElevatorParams.getLIFT_TRACTIORROPENUMBER());
        LIFT_MOTORTYPE.setText(mElevatorParams.getLIFT_MOTORTYPE());


        mUpdateBtn = findViewById(R.id.elevator_update_btn);
        mDeleteBtn = findViewById(R.id.elevator_delete_btn);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);

        switchEdit(false);
    }

    public void switchEdit(boolean isEdit){
        this.isEdit = isEdit;
        LIFT_ID.setEnabled(isEdit);
        LIFT_RATEDLOAD.setEnabled(isEdit);
        LIFT_RATEDSPEED.setEnabled(isEdit);
        LIFT_WIDTH.setEnabled(isEdit);
        LIFT_HEIGHT.setEnabled(isEdit);
        LIFT_VOLTAGE.setEnabled(isEdit);
        LIFT_CURRENT.setEnabled(isEdit);
        LIFT_TRACTORMODEL.setEnabled(isEdit);
        LIFT_TRACTIORWHEELDIAMETER.setEnabled(isEdit);
        LIFT_TRACTIORRATIO.setEnabled(isEdit);
        LIFT_TRACTIORTYPE.setEnabled(isEdit);
        LIFT_SAFETYGEARTYPE.setEnabled(isEdit);
        LIFT_TRACTIORNUMBER.setEnabled(isEdit);
        LIFT_TRACTIORROPENUMBER.setEnabled(isEdit);
        LIFT_MOTORTYPE.setEnabled(isEdit);
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
                    updateElevatorParams();
                }else{
                    switchEdit(true);
                }
                break;
            case R.id.elevator_delete_btn:
                if (isEdit){
                    switchEdit(false);
                }else{
                    deleteElevatorParams();
                }
                break;
        }
    }

    //删除电梯信息
    public void deleteElevatorParams(){
        DBManger.getInstance(this).deleteElevatorParams(mElevatorParams);
        finish();
    }

    //更新电梯信息
    public void updateElevatorParams(){
        mElevatorParams.setLIFT_ID(LIFT_ID.getEditableText().toString());
        mElevatorParams.setLIFT_RATEDLOAD(LIFT_RATEDLOAD.getEditableText().toString());
        mElevatorParams.setLIFT_RATEDSPEED(LIFT_RATEDSPEED.getEditableText().toString());
        mElevatorParams.setLIFT_WIDTH(LIFT_WIDTH.getEditableText().toString());
        mElevatorParams.setLIFT_HEIGHT(LIFT_HEIGHT.getEditableText().toString());
        mElevatorParams.setLIFT_VOLTAGE(LIFT_VOLTAGE.getEditableText().toString());
        mElevatorParams.setLIFT_CURRENT(LIFT_CURRENT.getEditableText().toString());
        mElevatorParams.setLIFT_TRACTORMODEL(LIFT_TRACTORMODEL.getEditableText().toString());
        mElevatorParams.setLIFT_TRACTIORWHEELDIAMETER(LIFT_TRACTIORWHEELDIAMETER.getEditableText().toString());
        mElevatorParams.setLIFT_TRACTIORRATIO(LIFT_TRACTIORRATIO.getEditableText().toString());
        mElevatorParams.setLIFT_TRACTIORTYPE(LIFT_TRACTIORTYPE.getEditableText().toString());
        mElevatorParams.setLIFT_SAFETYGEARTYPE(LIFT_SAFETYGEARTYPE.getEditableText().toString());
        mElevatorParams.setLIFT_TRACTIORNUMBER(LIFT_TRACTIORNUMBER.getEditableText().toString());
        mElevatorParams.setLIFT_MOTORTYPE(LIFT_MOTORTYPE.getEditableText().toString());

        DBManger.getInstance(this).updateElevatorParams(mElevatorParams);
    }
}
