package com.smart.elevator.data;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.Task;
import com.smart.elevator.navi.LocationMgr;
import com.smart.elevator.navi.PoiSearchMgr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataFactory {
    private Context mContext;
    public static  DataFactory instance;

    //搜索模块
    private PoiSearchMgr mPoiSearchMgr;
    //定位模块
    private LocationMgr mLocationMgr;

    private LatLng mCurrentPosition; //当前地点

    private Double mLongitude;
    private Double mLatitude;
    private String mKeyWord="电梯";

    public List<Elevator> mElevators = new ArrayList<>();
    public List<Task> mTasks = new ArrayList<>();


    public static DataFactory getInstance(Context mContext){
        if (instance == null){
            instance = new DataFactory(mContext);
        }
        return instance;
    };

    public DataFactory(Context mContext){
        this.mContext = mContext;
        initData();
    }

    public void initData(){
        mLocationMgr  = new LocationMgr(mContext);
        mPoiSearchMgr = new PoiSearchMgr(mContext);
        mPoiSearchMgr.setPoiListener(new PoiSearchMgr.PoiSearchListener() {

            @Override
            public void onSuccess(List<PoiItem> poiItems) {
                mElevators.clear();
                mTasks.clear();
                for (int i = 0;i<poiItems.size();i++){
                    PoiItem item = poiItems.get(i);
                    //生成电梯数据
                    LatLonPoint latLonPoint = item.getLatLonPoint();
                    String LIFT_ID = getRandomLIFT_ID();
                    Elevator elevator = new Elevator();
                    elevator.setLIFT_ID(LIFT_ID);
                    elevator.setLIFT_ADDRESSID(latLonPoint.getLatitude()+","+latLonPoint.getLongitude());
                    elevator.setLIFT_USER(item.getSnippet());
                    mElevators.add(elevator);
                    //生成电梯参数信息

                    //生成任务
                    Task task = new Task();
                    task.setElevator(elevator);
                    task.setLIFT_FORMID(getRandomLIFT_FORMID());
                    task.setLIFT_CURRENTSTATE("待接受");
                    task.setLIFT_SENDTIME(getSendTime());
                    mTasks.add(task);
                }

            }

            @Override
            public void onFail(String error) {

            }
        });
        getPosition();
    };

    public void doSearchQueryWithKeyWord(){
        mPoiSearchMgr.doSearchQuery(mKeyWord,mCurrentPosition.latitude,mCurrentPosition.longitude);
    }

    //获取定位信息并且查询当前的POI点周边
    public void getPosition(){
        mLocationMgr.getLonLat(mContext, new LocationMgr.LonLatListener() {
            @Override
            public void getLonLat(AMapLocation aMapLocation) {
                mLongitude = aMapLocation.getLongitude();
                mLatitude = aMapLocation.getLatitude();
                mCurrentPosition = new LatLng(mLatitude,mLongitude);
                doSearchQueryWithKeyWord();
            }
        });
    }

    //生成随机LIFT_ID
    public String getRandomLIFT_ID(){
        String strRand="L" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //生成随机LIFT_FORMID
    public String getRandomLIFT_FORMID(){
        String strRand="LF" ;
        for(int i=0;i<10;i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    //生成保修日期
    public String getSendTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
