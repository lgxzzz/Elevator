package com.smart.elevator.view;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.ElevatorPlaceActivity;
import com.smart.elevator.LoginActivity;
import com.smart.elevator.MainActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.Task;


public class TaskDetailDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private Button mElePlaceBtn;
    private Task mTask;

    public TaskDetailDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.view = LayoutInflater.from(context).inflate(layoutid, null);
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;

        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);


    }

    public void setData(Task task){
        this.mTask = task;
    }

    public void initView() {
        mSureBtn = view.findViewById(R.id.fast_navi_sure_btn);
        mCancelBtn = view.findViewById(R.id.fast_navi_cancel_btn);
        mElePlaceBtn = view.findViewById(R.id.elevator_place_btn);
        mElePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ElevatorPlaceActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("task",mTask);
                intent.putExtras(b);
                getContext().startActivity(intent);
            }
        });
        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(),"接受任务成功！",Toast.LENGTH_LONG).show();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}