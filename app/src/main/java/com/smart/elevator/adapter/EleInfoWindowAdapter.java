package com.smart.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;

public class EleInfoWindowAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;
    private TextView mEleAddTv;
    private TextView mEleFaultTv;
    Elevator elevator;

    public EleInfoWindowAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setElevator(Elevator elevator){
        this.elevator=elevator;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        mEleAddTv = view.findViewById(R.id.ele_address);
        mEleFaultTv = view.findViewById(R.id.ele_faulttype);
        if (elevator!=null){
            mEleAddTv.setText(elevator.getLIFT_USER());
//            mEleFaultTv.setText(elevator.getLIFT_PRODUCTDATE());
        }
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
