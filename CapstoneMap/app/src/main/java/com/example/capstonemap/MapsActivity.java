package com.example.capstonemap;

import static com.example.capstonemap.locationUpdate.LocationUpdateTime.locationRequestSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.capstonemap.click.ClickPolyLine;
import com.example.capstonemap.polyLine.PolyLine;
import com.example.capstonemap.databinding.ActivityMapsBinding;
import com.example.capstonemap.locationUpdate.UserUpdateInfo;
import com.example.capstonemap.locationUpdate.CourseInOut;
import com.example.capstonemap.locationUpdate.GeoFenceListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private UserUpdateInfo userUpdateInfo;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // FusedLocationProviderClient 및 LocationRequest 설정
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = locationRequestSetting();

        // 지오펜싱 초기화 및 리스너 설정
        CourseInOut courseInOut = new CourseInOut(new GeoFenceListener() {
            @Override
            public void onGeofenceEnter() {
                if (userUpdateInfo != null) {
                    userUpdateInfo.enterCourse();
                }
            }

            @Override
            public void onGeofenceExit() {
                if (userUpdateInfo != null) {
                    userUpdateInfo.exitCourse();
                }
            }
        });

        // UserUpdateInfo 객체 생성 및 연동
        userUpdateInfo = new UserUpdateInfo(courseInOut);

        // LocationCallback 설정
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null || userUpdateInfo == null) return;
                for (Location location : locationResult.getLocations()) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    float speed = location.getSpeed();
                    userUpdateInfo.updateUserInfo(latitude, longitude, speed);
                }
            }
        };

        // GoogleMap 초기화 요청
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // 지도에 기본 마커 추가
        LatLng location = new LatLng(37.506632, 126.960733);
        mMap.addMarker(new MarkerOptions().position(location).title("Jung-dae Hospital"));

        // PolyLine 호출 - GoogleMap 초기화 이후 실행
        LatLng origin = new LatLng(35.657267698447065, 139.75021273761237);
        LatLng destination = new LatLng(35.65130445276861, 139.77898822344184);
        PolyLine.getDirections(origin, destination);

        // 클릭 이벤트 설정
        ClickPolyLine.clickPolyLine(mMap);

        // 위치 업데이트 시작
        startLocationUpdates();
    }

    private void enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            try {
                if (mMap != null) {
                    mMap.setMyLocationEnabled(true);
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, location -> {
                                if (location != null) {
                                    LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                                }
                            });
                }
            } catch (SecurityException e) {
                Log.e("MapsActivity", "Location permission denied", e);
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            try {
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            } catch (SecurityException e) {
                Log.e("MapsActivity", "Location permission denied for location updates", e);
                Toast.makeText(this, "Location permission denied for location updates", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission was not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static GoogleMap getMap() {
        return mMap;
    }
}