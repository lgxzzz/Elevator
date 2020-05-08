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
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.ElevatorParams;
import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.view.TitleView;

/**
 * 电梯信息展示activity
 *
 * **/
public class ElevotorParamsActivity extends AppCompatActivity {
    private TitleView mTitleView;
    private TextView mLIFT_ID;
    private TextView mLIFT_RATEDLOAD;
    private TextView mLIFT_RATEDSPEED;
    private TextView mLIFT_WIDTH;
    private TextView mLIFT_HEIGHT;
    private TextView mLIFT_VOLTAGE;
    private TextView mLIFT_CURRENT;
    private TextView mLIFT_TRACTORMODEL;
    private TextView mLIFT_TRACTIORWHEELDIAMETER;
    private TextView mLIFT_TRACTIORRATIO;
    private TextView mLIFT_TRACTIORTYPE;
    private TextView mLIFT_BUFFERTYPE;
    private TextView mLIFT_SAFETYGEARTYPE;
    private TextView mLIFT_TRACTIORNUMBER;
    private TextView mLIFT_TRACTIORROPENUMBER;
    private TextView mLIFT_MOTORTYPE;
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


        mLIFT_ID = findViewById(R.id.LIFT_ID);
        mLIFT_RATEDLOAD = findViewById(R.id.LIFT_RATEDLOAD);
        mLIFT_RATEDSPEED = findViewById(R.id.LIFT_RATEDSPEED);
        mLIFT_WIDTH = findViewById(R.id.LIFT_WIDTH);
        mLIFT_HEIGHT = findViewById(R.id.LIFT_HEIGHT);
        mLIFT_VOLTAGE = findViewById(R.id.LIFT_VOLTAGE);
        mLIFT_CURRENT = findViewById(R.id.LIFT_CURRENT);
        mLIFT_TRACTORMODEL = findViewById(R.id.LIFT_TRACTORMODEL);
        mLIFT_TRACTIORWHEELDIAMETER = findViewById(R.id.LIFT_TRACTIORWHEELDIAMETER);
        mLIFT_TRACTIORRATIO = findViewById(R.id.LIFT_TRACTIORRATIO);
        mLIFT_TRACTIORTYPE = findViewById(R.id.LIFT_TRACTIORTYPE);
        mLIFT_BUFFERTYPE = findViewById(R.id.LIFT_BUFFERTYPE);
        mLIFT_SAFETYGEARTYPE = findViewById(R.id.LIFT_SAFETYGEARTYPE);
        mLIFT_TRACTIORNUMBER = findViewById(R.id.LIFT_TRACTIORNUMBER);
        mLIFT_TRACTIORROPENUMBER = findViewById(R.id.LIFT_TRACTIORROPENUMBER);
        mLIFT_MOTORTYPE = findViewById(R.id.LIFT_MOTORTYPE);

        Task mTask = (Task) getIntent().getExtras().getSerializable("task");
        ElevatorParams elevatorParams = DBManger.getInstance(getApplication()).getElevatorParamsByID(mTask.getLIFT_ID());
        mLIFT_ID.setText(elevatorParams.getLIFT_ID());
        mLIFT_RATEDLOAD.setText(elevatorParams.getLIFT_RATEDLOAD());
        mLIFT_RATEDSPEED.setText(elevatorParams.getLIFT_RATEDSPEED());
        mLIFT_WIDTH.setText(elevatorParams.getLIFT_WIDTH());
        mLIFT_HEIGHT.setText(elevatorParams.getLIFT_HEIGHT());
        mLIFT_VOLTAGE.setText(elevatorParams.getLIFT_VOLTAGE());
        mLIFT_CURRENT.setText(elevatorParams.getLIFT_CURRENT());
        mLIFT_TRACTORMODEL.setText(elevatorParams.getLIFT_TRACTORMODEL());
        mLIFT_TRACTIORWHEELDIAMETER.setText(elevatorParams.getLIFT_TRACTIORWHEELDIAMETER());
        mLIFT_TRACTIORRATIO.setText(elevatorParams.getLIFT_TRACTIORRATIO());
        mLIFT_TRACTIORTYPE.setText(elevatorParams.getLIFT_TRACTIORTYPE());
        mLIFT_BUFFERTYPE.setText(elevatorParams.getLIFT_BUFFERTYPE());
        mLIFT_SAFETYGEARTYPE.setText(elevatorParams.getLIFT_SAFETYGEARTYPE());
        mLIFT_TRACTIORNUMBER.setText(elevatorParams.getLIFT_TRACTIORNUMBER());
        mLIFT_TRACTIORROPENUMBER.setText(elevatorParams.getLIFT_TRACTIORROPENUMBER());
        mLIFT_MOTORTYPE.setText(elevatorParams.getLIFT_MOTORTYPE());
    }
}
