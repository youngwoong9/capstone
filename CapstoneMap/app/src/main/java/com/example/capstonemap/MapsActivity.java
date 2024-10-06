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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
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
