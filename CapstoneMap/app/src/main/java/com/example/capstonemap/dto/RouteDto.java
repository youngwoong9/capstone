package com.example.capstonemap.dto;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RouteDto {
    private Long id;
    String name;
    private String encodedPath;
    private ArrayList<Double[]> locationList;

    /* 나중에 추가할거
    루트길이, 시작점, 루트 이름 <- 나중에 지어주는 칸을 만들어야함 등등
     */

    public RouteDto(String name, String encodedPath, ArrayList<Double[]> locationList){
        this.name = name;
        this.encodedPath = encodedPath;
        this.locationList = locationList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEncodedPath(String encodedPath) {
        this.encodedPath = encodedPath;
    }

    public void setLocationList(ArrayList<Double[]> locationList) {
        this.locationList = locationList;
    }
}
