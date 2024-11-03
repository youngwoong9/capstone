package com.example.capstonemap.locationUpdate;

import java.util.ArrayList;

//업데이트한 정보를 담는 dto
public class UserUpdateDto {
    private double averageSpeed; // 평균 속도
    private long elapsedTime;   // 경과 시간 (초 단위)
    private long startedTime;
    private double latitude;    // 실시간 위도
    private double longitude;   // 실시간 경도
    private ArrayList<Double> speedList=new ArrayList<>();        // 속도 배열
    private ArrayList<Double> latitudeList=new ArrayList<>();
    private ArrayList<Double> longitudeList=new ArrayList<>();
    private double speed;

    // 기본 생성자
    public UserUpdateDto() {}

    // Getter 및 Setter
    public double getAverageSpeed() { return averageSpeed; }
    public void setAverageSpeed(double averageSpeed) { this.averageSpeed = averageSpeed; }
    public long getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(long elapsedTime) { this.elapsedTime = elapsedTime; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude;
    latitudeList.add(latitude);}
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude;
    longitudeList.add(longitude);}
    public ArrayList<Double> getSpeedList() { return speedList; }
    public double getSpeed(){return speed;}
    public void setSpeed(double speed) {
        if(speedList==null){
            speedList=new ArrayList<>();
        }
        this.speed = speed;
        speedList.add(speed);}
    public void setStartedTime(long startedTime){this.startedTime = startedTime;}
    public long getStartedTime(){return startedTime;}

    public ArrayList<Double> getLatitudeList() {
        return latitudeList;
    }

    public ArrayList<Double> getLongitudeList() {
        return longitudeList;
    }
}
