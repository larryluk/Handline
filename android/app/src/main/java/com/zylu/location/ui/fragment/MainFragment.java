package com.zylu.location.ui.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.zylu.location.R;
import com.zylu.location.event.LocationEvent;
import com.zylu.location.event.WaitLocationEvent;
import com.zylu.location.mvp.view.IFragmentContentManage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Larry on 2016/12/11.
 */

public class MainFragment extends Fragment implements AMapLocationListener {
    private final int LOCATION_REQUEST = 1;
    private static final String TAG = "MainFragment";

    @BindView(R.id.map) MapView map;
    private AMap mMap = null;
    private boolean changeCameraFlag = true;
    private ProgressDialog dialog;

    private IFragmentContentManage fragmentContentManage;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private Marker maker;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private LocationEvent event;

    /**
     * 查询用户权限
     */
    private void checkPermission() {
        //申请权限
        for (String permission : needPermissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        LOCATION_REQUEST);//自定义的code
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);
        init(savedInstanceState);
        return view;
    }

    private void init(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        map.onCreate(savedInstanceState);

        //初始化地图变量
        mMap = map.getMap();
        //去掉缩放按钮
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(false);
        //检查权限
        checkPermission();

        location();

        Toast.makeText(getActivity(), "定位中...", Toast.LENGTH_SHORT).show();


    }


    private void updateMap(@NonNull double latitude, @NonNull double longitude,
                           String poiName, String cityName) {

        if(changeCameraFlag) {
            CameraUpdate update =  CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    new LatLng(latitude,longitude),//新的中心点坐标
                    16.5f, //新的缩放级别
                    0, //俯仰角0°~45°（垂直与地图时为0）
                    0  ////偏航角 0~360° (正北方为0)
            ));
            mMap.moveCamera(update);
            changeCameraFlag = false;
        }

        if(maker != null) maker.destroy();

        getActivity().setTitle(poiName);
        LatLng latLng = new LatLng(latitude,longitude);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.title(poiName).snippet(cityName + "：" + latitude + ", " + longitude);
        markerOption.position(latLng);
        markerOption.draggable(true);

        maker = mMap.addMarker(markerOption);
    }

    private void location() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(10000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LocationEvent event) {
        this.event = event;
        changeCameraFlag = true;
        mLocationClient.stopLocation();
        updateMap(event.getLatitude(), event.getLongitude(), event.getPoiName(), event.getCityName());

        if(dialog != null) dialog.dismiss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WaitLocationEvent event) {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("正在定位好友...");
        dialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        map.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentContentManage = null;
        if(mLocationClient != null) mLocationClient.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentContentManage) {
            fragmentContentManage = (IFragmentContentManage) context;
        }

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                String poiName = aMapLocation.getPoiName();
                String cityName = aMapLocation.getCity();

                updateMap(latitude, longitude, poiName, cityName);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        switch (requestCode) {
            case LOCATION_REQUEST:
                location();
                break;

            default:
        }
    }
}
