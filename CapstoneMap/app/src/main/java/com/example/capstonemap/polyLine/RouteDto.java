package com.example.capstonemap.polyLine;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDto {
    private Long id;
    private String name;
    private String encodedPath;
    private ArrayList<Double[]> locationList;
    private String userId;


    /* 나중에 추가할거
    루트길이, 시작점, 루트 이름 <- 나중에 지어주는 칸을 만들어야함 등등
     */

    public RouteDto(String name, String encodedPath, ArrayList<Double[]> locationList){
        this.name = name;
        this.encodedPath = encodedPath;
        this.locationList = locationList;
    }

}
