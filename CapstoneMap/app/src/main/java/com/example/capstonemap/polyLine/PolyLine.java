package com.example.capstonemap.polyLine;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.capstonemap.MakeApiRequest.MakeApiRequest;
import com.example.capstonemap.BuildConfig;
import com.example.capstonemap.MapsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

// 폴리라인을 길에 맞춰 그리는 함수 구현

public class PolyLine {

    // url에 응답을 요청하는 함수
    public static void getDirections(LatLng origin, LatLng destination) {
        String apiKey = BuildConfig.MAPS_API_KEY;
        String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +"&key=" + apiKey;

        Log.d("DIRECTIONS_API_REQUEST", "Request URL: " + urlString);

        Executors.newSingleThreadExecutor().execute(() -> {
            String response = MakeApiRequest.makeApiRequest(urlString);
            if (response != null) {
                Log.d("DIRECTIONS_API_RESPONSE", response);
                new Handler(Looper.getMainLooper()).post(() -> drawPolyline(response));
            }else{
                Log.e("DIRECTIONS_API", "No response from the API");
            }
        });
    }

    private static void drawPolyline(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray routes = jsonObject.getJSONArray("routes");
            List<LatLng> path = new ArrayList<>();

            //로그 추가 zero_results 반환때문에
            String status = jsonObject.getString("status");
            Log.d("DIRECTIONS_API_STATUS", "API Response Status: " + status);

            if (!"OK".equals(status)) {
                Log.e("DIRECTIONS_API_STATUS", "Error: API response status is " + status);
                if ("ZERO_RESULTS".equals(status)) {
                    Log.e("DIRECTIONS_API", "No routes found between the specified origin and destination.");
                }
                return;
            }

            //경로 데이터를 파싱함
            if (routes.length() > 0) {
                JSONObject route = routes.getJSONObject(0);
                JSONArray legs = route.getJSONArray("legs");
                for (int i = 0; i < legs.length(); i++) {
                    JSONArray steps = legs.getJSONObject(i).getJSONArray("steps");
                    for (int j = 0; j < steps.length(); j++) {
                        String polyline = steps.getJSONObject(j).getJSONObject("polyline").getString("points");
                        path.addAll(decodePolyline(polyline));
                    }
                }
            }

            PolylineOptions polylineOptions = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            MapsActivity.getMap().addPolyline(polylineOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //인코딩된 String 폴리라인을 latlng로 변환
    private static List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((lat / 1E5), (lng / 1E5));
            poly.add(p);
        }

        return poly;
    }
}
