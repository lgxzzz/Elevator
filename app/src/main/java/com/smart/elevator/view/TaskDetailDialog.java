package com.smart.elevator.view;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.elevator.ElevatorPlaceActivity;
import com.smart.elevator.ElevotorParamsActivity;
import com.smart.elevator.LoginActivity;
import com.smart.elevator.MainActivity;
import com.smart.elevator.R;
import com.smart.elevator.bean.Task;
import com.smart.elevator.bean.User;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.util.NotifyState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
    private TextView mDetailDes;
    private LinearLayout mAceeptLayout;
    private LinearLayout mFaultLayout;
    private LinearLayout mSendTimeLayout;
    private LinearLayout mStateLayout;
    private LinearLayout mSelectFaultLayout;
    private LinearLayout mSelectPersonLayout;
    private LinearLayout mSelectTimeLayout;
    private Spinner mFaultSp;
    private Spinner mPersonSp;
    private Task mTask;
    private User mUSer;
    String mSelectPseron;
    String mSelectFault;

    List<String> mPersonData =new ArrayList<>();
    List<String> mFaultData =new ArrayList<>();

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
        mUSer = DBManger.getInstance(getContext()).mUser;

        mTaskId.setText(mTask.getLIFT_FORMID());
        mEleId.setText(mTask.getElevator().getLIFT_ID());
        mFaulteTimeId.setText(mTask.getLIFT_FAIULTTIME());
        mSendTimeId.setText(mTask.getLIFT_SENDTIME());
        mState.setText(mTask.getLIFT_CURRENTSTATE());
        mFault.setText(mTask.getLIFT_FAULTTYPE());


        if (task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_TIMEOUT)){
            if (mUSer.getRole().equals("维保接待员")){
                mAceeptLayout.setVisibility(View.VISIBLE);
                mDetailDes.setText("是否重新分配任务");
                mSureBtn.setText("重新分配");
                mSureBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        mTask.setLIFT_CURRENTSTATE(Constant.TASK_STATE_WAITING);
                        DBManger.getInstance(getContext()).updateTask(mTask);
                        //通知刷新数据
                        NotifyState.notifyRefreshData(getContext());
                    }
                });
            }
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_SIGN)){
            if (mUSer.getRole().equals("维保人员")){
                mAceeptLayout.setVisibility(View.VISIBLE);
                mDetailDes.setText("是否提交任务");
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
            }
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_FINISH)){
            mAceeptLayout.setVisibility(View.GONE);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING_SIGN)){
            mAceeptLayout.setVisibility(View.GONE);
        }else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_WAITING)){
            if (mUSer.getRole().equals("维保人员")){
                mAceeptLayout.setVisibility(View.VISIBLE);
                mDetailDes.setText("是否接受任务");
                mSureBtn.setText("接受");
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
            }

        } else if(task.getLIFT_CURRENTSTATE().equals(Constant.TASK_STATE_REPORT)){
            mAceeptLayout.setVisibility(View.VISIBLE);
            mFaultLayout.setVisibility(View.GONE);
            mStateLayout.setVisibility(View.GONE);
            mSendTimeLayout.setVisibility(View.GONE);
            mSelectFaultLayout.setVisibility(View.VISIBLE);
            mSelectPersonLayout.setVisibility(View.VISIBLE);
//            mSelectTimeLayout.setVisibility(View.VISIBLE);

            mSureBtn.setText("制定任务");
            mSureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dismiss();
                   mTask.setLIFT_FAULTTYPE(mSelectFault);
                   mTask.setLIFT_PROCESSOR(mSelectPseron);
                   mTask.setLIFT_SENDTIME(getSendTime());
                   mTask.setLIFT_CURRENTSTATE(Constant.TASK_STATE_WAITING);
                   DBManger.getInstance(getContext()).updateTask(mTask);
                    //通知刷新数据
                    NotifyState.notifyRefreshData(getContext());
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
        mSelectFaultLayout = view.findViewById(R.id.select_fault_layout);
        mSelectPersonLayout = view.findViewById(R.id.select_person_layout);
        mSelectTimeLayout = view.findViewById(R.id.select_time_layout);
        mFaultLayout = view.findViewById(R.id.fault_layout);
        mSendTimeLayout = view.findViewById(R.id.send_time_layout);
        mStateLayout = view.findViewById(R.id.state_layout);
        mDetailDes = view.findViewById(R.id.task_detail_des);




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


        mFaultSp = view.findViewById(R.id.select_task_fault_sp);
        mPersonSp = view.findViewById(R.id.select_task_person_sp);

        mFaultData.add("故障老旧");
        mFaultData.add("线路老化");
        mFaultData.add("按键失灵");

        mPersonData = DBManger.getInstance(getContext()).getUsersNameNameByRole("维保人员");

        SpinnerAdapter adapter = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item,mFaultData);
        mFaultSp.setAdapter(adapter);
        mSelectFault = mFaultData.get(0);

        mFaultSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectFault = mFaultData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (mPersonData.size()>0){
            SpinnerAdapter adapter1 = new ArrayAdapter<String>(getContext(),R.layout.simple_spinner_item,mPersonData);
            mPersonSp.setAdapter(adapter1);
            mSelectPseron = mPersonData.get(0);

            mPersonSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSelectPseron = mPersonData.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    //派单日期
    public String getSendTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }

}