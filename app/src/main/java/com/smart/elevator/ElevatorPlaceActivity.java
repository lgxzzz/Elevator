package com.smart.elevator;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.smart.elevator.adapter.EleInfoWindowAdapter;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.Task;
import com.smart.elevator.view.TitleView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 *地图显示电梯位置信息activity
 *
 *
 * **/
public class ElevatorPlaceActivity extends Activity{
    private MapView mMapView = null;
    private AMap mAMap;
    private TitleView mTitleView;
    private Marker mLocationMarker; // 选择的点
    private UiSettings mUiSettings;
    //自定义的地图弹窗布局
    private EleInfoWindowAdapter mAdapter;
    private Elevator mElevator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_place);
        initView(savedInstanceState);
        initData();
    }

    public void initView(Bundle savedInstanceState){
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
//        mUiSettings = mAMap.getUiSettings();
//        mUiSettings.setZoomControlsEnabled(false); //隐藏缩放控件
        mTitleView = findViewById(R.id.title_view);
        mTitleView.setTitle("电梯位置");
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    };

    public void initData(){
        //获取电梯的位置信息
        mElevator = (Elevator) getIntent().getExtras().getSerializable("elevator");
        String address = mElevator.getLIFT_ADDRESSID();
        String[] params= address.split(",");
        double lat = Double.parseDouble(params[1]);
        double lon = Double.parseDouble(params[0]);
        addmark(lat,lon);
    };

    //增加图标
    private void addmark(double latitude, double longitude) {
        mAMap.clear();
        mLocationMarker = mAMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.navi_icon_1)))
                .draggable(true));
        LatLng mPosition = mLocationMarker.getPosition();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mPosition.latitude, mPosition.longitude), 18));
        mAMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showInfoWindow();
                return false;
            }
        });
    }

    //显示info窗口
    public void showInfoWindow(){
        LatLng mPosition = mLocationMarker.getPosition();
        mAdapter = new EleInfoWindowAdapter(this);
        mAMap.setInfoWindowAdapter(mAdapter);
        mAdapter.setElevator(mElevator);
    }

}
