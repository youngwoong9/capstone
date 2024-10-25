package com.example.capstonemap.click;

import com.example.capstonemap.polyLine.PolyLine;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

public class ClickPolyLine {
    public static void clickPolyLine(GoogleMap mMap) {
        final LatLng[] origin = new LatLng[1];  // 출발지를 저장할 배열
        final LatLng[] destination = new LatLng[1];  // 도착지를 저장할 배열

        // 지도를 길게 눌렀을 때 마커를 추가하고 폴리라인을 그리는 기능 설정
        mMap.setOnMapLongClickListener(latLng -> {
            if (origin[0] == null) {
                // 첫 번째 클릭 시: 출발지 설정
                origin[0] = latLng;

                // 지금 꾹 누른 출발지가 어디인지 나타나게.

            } else if (destination[0] == null) {
                // 두 번째 클릭 시: 도착지 설정
                destination[0] = latLng;

                // 출발지와 도착지를 이용하여 폴리라인 그리기
                PolyLine.getDirections(origin[0], destination[0]);
            } else {
                // 기존 마커와 폴리라인 초기화 후, 새로운 출발지 설정
                PolyLine.removePolyline(PolyLine.getLastEncoded());
                origin[0] = latLng;
                destination[0] = null;  // 새로운 출발지가 설정되었으므로 도착지는 null로 초기화
            }
        });
    }
}
