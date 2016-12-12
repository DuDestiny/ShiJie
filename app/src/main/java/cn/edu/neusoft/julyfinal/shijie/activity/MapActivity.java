package cn.edu.neusoft.julyfinal.shijie.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import cn.edu.neusoft.julyfinal.shijie.R;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    //定位相关
    private LocationClient locationClient;
    private MyLocationListener mylocationlistener;
    private boolean isFirstin=true;

    //记录最新经纬度
    private double MLatitude;
    private double MLongtitude;

    private float CurrentX;
    //自定义图标
    private BitmapDescriptor mIconLcation;

    private MyOrientationListener myOrientationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        initView();
        initLocation();
    }

    private void initLocation() {
        locationClient=new LocationClient(this);
        mylocationlistener=new MyLocationListener();
        locationClient.registerLocationListener(mylocationlistener);

        LocationClientOption option= new LocationClientOption();
        //坐标类型
        option.setCoorType("bd09ll");

        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        //初始化图标
        mIconLcation= BitmapDescriptorFactory.fromResource(R.drawable.jt);

        myOrientationListener=new MyOrientationListener(MapActivity.this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                CurrentX=x;
            }
        });
    }

    private void initView() {
        mapView= (MapView) findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        MapStatusUpdate msu =MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(msu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        baiduMap.setMyLocationEnabled(true);
        if (!locationClient.isStarted());
        locationClient.start();
        //开启方向传感器
        myOrientationListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        //Activity销毁时地图销毁
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        locationClient.stop();
        //关闭传感器
        myOrientationListener.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.putong:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.weixing:
                baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.jiaotong:
                if(baiduMap.isTrafficEnabled()){
                    baiduMap.setTrafficEnabled(false);
                    item.setTitle("关闭");
                }else {
                    baiduMap.setTrafficEnabled(true);
                    item.setTitle("开启");
                }
                break;
            case R.id.backtome:
                centertome();
                break;
        }
        return super .onOptionsItemSelected(item);
    }
    //定位到我的位置
    private void centertome(){
        LatLng latLng=new LatLng(MLatitude,MLongtitude);
        MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(msu);
    }
    private class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data=new MyLocationData.Builder()
                    .direction(CurrentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(data);
            //指示图标
            MyLocationConfiguration config=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,mIconLcation);
           baiduMap.setMyLocationConfigeration(config);
            //更新经纬度
            MLatitude=bdLocation.getLatitude();
            MLongtitude=bdLocation.getLongitude();
            if (isFirstin){
                LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(msu);
                isFirstin=false;

                Toast.makeText(MapActivity.this,bdLocation.getAddrStr(),Toast.LENGTH_SHORT).show();
            }

        }
    }
}
