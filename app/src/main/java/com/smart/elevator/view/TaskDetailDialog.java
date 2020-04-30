package com.smart.elevator.view;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.ElevatorPlaceActivity;
import com.smart.elevator.ElevotorParamsActivity;
import com.smart.elevator.LoginActivity;
import com.smart.elevator.MainActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.Task;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.util.NotifyState;


public class TaskDetailDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private Button mElePlaceBtn;
    private Button mEleParamBtn;
    private TextView mTaskId;
    private TextView mEleId;
    private TextView mFaulteTimeId;
    private TextView mSendTimeId;
    private TextView mState;
    private TextView mFault;
    private LinearLayout mAceeptLayout;
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

        mTaskId.setText(mTask.getLIFT_FORMID());
        mEleId.setText(mTask.getElevator().getLIFT_ID());
        mFaulteTimeId.setText(mTask.getLIFT_FAIULTTIME());
        mSendTimeId.setText(mTask.getLIFT_SENDTIME());
        mState.setText(mTask.getLIFT_CURRENTSTATE());
        mFault.setText(mTask.getLIFT_FAULTTYPE());
        if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_TIMEOUT)){
            mAceeptLayout.setVisibility(View.GONE);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_SIGN)){
            mSureBtn.setText("提交");
            mSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    mTask.setLIFT_CURRENTSTATE(Constant.TASK_STATE_FINISH);
                    DBManger.getInstance(getContext()).updateTaskFinish(mTask);
                    Toast.makeText(getContext(),"提交任务成功！",Toast.LENGTH_LONG).show();
                    //通知刷新数据
                    NotifyState.notifyRefreshData(getContext());
                }
            });
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_FINISH)){
            mAceeptLayout.setVisibility(View.GONE);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING_SIGN)){
            mAceeptLayout.setVisibility(View.GONE);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING)){
            mSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    mTask.setLIFT_CURRENTSTATE(Constant.TASK_STATE_WAITING_SIGN);
                    DBManger.getInstance(getContext()).insertRepairSign(mTask);
                    Toast.makeText(getContext(),"接受任务成功！",Toast.LENGTH_LONG).show();
                    //通知刷新数据
                    NotifyState.notifyRefreshData(getContext());
                }
            });
        } else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_REPORT)){
            mSureBtn.setText("制定任务");
            mSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(),ElevatorPlaceActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("task",mTask);
                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }
            });
        }
    }

    public void initView() {

        mTaskId = view.findViewById(R.id.task_id_tv);
        mEleId = view.findViewById(R.id.ele_id_tv);
        mFaulteTimeId = view.findViewById(R.id.faulttime_tv);
        mSendTimeId = view.findViewById(R.id.sendtime_tv);
        mState = view.findViewById(R.id.task_state);
        mFault = view.findViewById(R.id.task_fault);
        mAceeptLayout = view.findViewById(R.id.task_acept_layout);



        mSureBtn = view.findViewById(R.id.fast_navi_sure_btn);
        mCancelBtn = view.findViewById(R.id.fast_navi_cancel_btn);
        mElePlaceBtn = view.findViewById(R.id.elevator_place_btn);
        mEleParamBtn = view.findViewById(R.id.elevator_params_btn);
        mElePlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(),ElevatorPlaceActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("task",mTask);
                intent.putExtras(b);
                getContext().startActivity(intent);
                dismiss();
            }
        });
        mEleParamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ElevotorParamsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("task",mTask);
                intent.putExtras(b);
                getContext().startActivity(intent);
                dismiss();
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