package com.example.capstonemap;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.os.AsyncTask;
import android.graphics.Color;

import com.example.capstonemap.polyLine.PolyLine;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.capstonemap.databinding.ActivityMapsBinding;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    static private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //google 맵인 mMap을 쓰는 코드

        mMap = googleMap;
        enableMyLocation();

        // 줌버튼
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // 마커를 추가하는 코드(addMarker = 마커찍기함수)
        LatLng location = new LatLng(37.506632, 126.960733);
        mMap.addMarker(new MarkerOptions().position(location).title("Jung-dae Hospital"));

        // 폴리라인을 추가하는 코드
        LatLng origin = new LatLng(35.657267698447065, 139.75021273761237);
        LatLng destination = new LatLng(35.65130445276861, 139.77898822344184);
        Log.d("MAP_ACTIVITY", "Calling getDirections");
        PolyLine.getDirections(origin, destination);
    }

    private void enableMyLocation() {
        // 실시간 gps 위치 표시하는 기능 위치 허용 안했으면 허용할건지 묻고, 허용 했으면 실시간 위치 켬.
        // 실시간 gps 위치를 불러올 수 있다면 기본적으로 그 위치가 지도에 뜨게 함.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        }
                    });
        }
    }

    private void getCurrentLocation() {
        // 위치 권한이 있는지 확인 후 현재 위치 가져오기
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // 현재 위치를 LatLng 객체로 변환
                            LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                            // 현재 위치로 카메라 이동
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                        } else {
                            Log.e("MAP_ACTIVITY", "현재 위치를 가져올 수 없습니다.");
                        }
                    })
                    .addOnFailureListener(e -> Log.e("MAP_ACTIVITY", "현재 위치 가져오기 실패: " + e.getMessage()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission granted, enable location layer
                enableMyLocation();
            }
        }
    }

    public static GoogleMap getMap(){
        return mMap;
    }
}
