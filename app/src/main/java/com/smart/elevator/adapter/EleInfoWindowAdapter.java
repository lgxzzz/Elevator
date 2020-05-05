package com.smart.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.Task;

public class EleInfoWindowAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;
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
    Task mTask;
    Elevator mElevator;

    public EleInfoWindowAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setTask(Task mTask){
        this.mTask = mTask;
        this.mElevator=mTask.getElevator();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        LIFT_ID = view.findViewById(R.id.LIFT_ID);
        LIFT_IDCODE = view.findViewById(R.id.LIFT_IDCODE);
        LIFT_USER = view.findViewById(R.id.LIFT_USER);
        LIFT_AREAID = view.findViewById(R.id.LIFT_AREAID);
        LIFT_ADDRESSID = view.findViewById(R.id.LIFT_ADDRESSID);
        LIFT_MAINTENANCENAME_ID = view.findViewById(R.id.LIFT_MAINTENANCENAME_ID);
        LIFT_BRANDID = view.findViewById(R.id.LIFT_BRANDID);
        LIFT_PRODUCT = view.findViewById(R.id.LIFT_PRODUCT);
        LIFT_PRODUCTDATE = view.findViewById(R.id.LIFT_PRODUCTDATE);
        LIFT_STATUS = view.findViewById(R.id.LIFT_STATUS);
        if (mElevator!=null){
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
            LIFT_STATUS.setText(mTask.getLIFT_CURRENTSTATE());
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
