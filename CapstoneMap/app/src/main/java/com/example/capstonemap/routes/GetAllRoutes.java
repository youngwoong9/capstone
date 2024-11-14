package com.example.capstonemap.routes;

import android.util.Log;
import android.view.View;

import androidx.core.util.Consumer;

import com.example.capstonemap.databinding.ActivityMapsBinding;
import com.example.capstonemap.polyLine.PolyLine;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

//RouteApiService의 getAllRoutes를 쓰는 클래스
public class GetAllRoutes {
    static RouteRepository routeRepository=new RouteRepository();
    static List<RouteDto> routeDtoList= new ArrayList<>();

    // 이름은 getAllRoutes지만 사실 routeDtoList 필드에 저장하는(set) 역할도 함.
    private static List<RouteDto> getAllRoutes() {
        routeRepository.getAllRoutes(
                // 성공 시 routeDtoList에 데이터 저장
                routes -> {
                    routeDtoList.clear();
                    routeDtoList.addAll(routes);
                    System.out.println("모든 루트가 routeDtoList에 저장되었습니다.");

                    drawAllRoutes(routeDtoList);
                },
                // 실패 시 처리
                () -> {
                    System.out.println("루트 조회에 실패했습니다.");
                }
        );

        return routeDtoList;
    }

    // 이 함수를 가까운 루트 3개의 경우 폴리라인을 그리는 로직으로 바꾸면 될듯
    // Double[] location을 보면 아직 2개의 점만으로 진행함. 나중에 locationList가 3개를 넘어갈경우 문제가 생길거임.
    // routeDto는 폴리라인 단위임 즉 routeDtoList의 인덱스가 5이면 현재 5개의 폴리라인이 있다는거임.
    private static void drawAllRoutes(List<RouteDto> routeDtoList){
        for(RouteDto routeDto : routeDtoList){
            List<Double[]> locationList = routeDto.getLocationList();
            LatLng[] latlngs=new LatLng[locationList.size()];

            for (int i = 0; i < locationList.size(); i++) {
                Double[] location = locationList.get(i);
                latlngs[i] = new LatLng(location[0], location[1]);
            }

            PolyLine.getDirections(latlngs[0], latlngs[1]);
        }
    }

    public static void getAllRoutesButton(ActivityMapsBinding binding){
        binding.GetAllRoutesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // GetAllRoutes의 getAllRoutes 메서드를 호출하여 루트 가져오기
                        getAllRoutes();
                    }
                }
        );
    }

}
