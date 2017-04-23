package com.zylu.location.event;

/**
 * Created by Administrator on 2017/2/23.
 */

public class LocationEvent {
    private double latitude ;   //纬度
    private double longitude;   //经度
    private String poiName;
    private String cityName;

    public LocationEvent(double latitude, double longitude, String poiName, String cityName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.poiName = poiName;
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
