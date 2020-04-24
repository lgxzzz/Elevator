package com.smart.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;

public class EleInfoWindowAdapter implements AMap.InfoWindowAdapter, View.OnClickListener {

    private Context mContext;

    public EleInfoWindowAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setElevator(Elevator elevator){

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
