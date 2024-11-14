package com.example.capstonemap;

import static com.example.capstonemap.locationUpdate.LocationUpdateTime.locationRequestSetting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.provider.ProviderProperties;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.capstonemap.polyLine.ClickPolyLine;
import com.example.capstonemap.polyLine.PolyLine;
import com.example.capstonemap.databinding.ActivityMapsBinding;
import com.example.capstonemap.locationUpdate.UserUpdateInfo;
import com.example.capstonemap.locationUpdate.CourseInOut;
import com.example.capstonemap.locationUpdate.GeoFenceListener;
import com.example.capstonemap.routes.DeleteRoute;
import com.example.capstonemap.routes.GetAllRoutes;
import com.example.capstonemap.user.GetUserRoutes;
import com.example.capstonemap.user.UserDto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final double MOCK_SPEED_M_S = 4.167; // 시속 15km/h를 m/s로 변환
    private static final String MOCK_PROVIDER = "mockProvider";
    private static GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private UserUpdateInfo userUpdateInfo;
    private LocationCallback locationCallback;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private double latitude = 37.506632;  // 시작 위도
    private double longitude = 126.960733;  // 시작 경도
    private final Handler handler = new Handler();

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

        // LocationManager 초기화 및 모의 위치 제공자 설정
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setupMockProvider();

        // GoogleMap 초기화 요청
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();

        mMap.getUiSettings().setZoomControlsEnabled(true);

        //임시적인 유저생성 지워야함.
        UserDto userDto=new UserDto(1L, "a", "a");

        // 지도에 기본 마커 추가
        LatLng location = new LatLng(37.506632, 126.960733);
        mMap.addMarker(new MarkerOptions().position(location).title("Jung-dae Hospital"));

        // PolyLine 호출 - GoogleMap 초기화 이후 실행
        LatLng origin = new LatLng(35.657267698447065, 139.75021273761237);
        LatLng destination = new LatLng(35.65130445276861, 139.77898822344184);
        PolyLine.getDirections(origin, destination);

        // 클릭 이벤트 설정
        ClickPolyLine.clickPolyLine(mMap, userDto);

        // 위치 업데이트 시작
        startLocationUpdates();

        // 모의 이동
        startMockMovement();

        //AllRoutesButton(임시), User의 id를 매개변수로 받는 getUesrRoutesButton(임시)
        GetAllRoutes.getAllRoutesButton(binding);
        GetUserRoutes.getUserRoutesButton(binding, userDto.getId());

        DeleteRoute.deleteButton(binding,1L, 1L);

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
                }
            } catch (SecurityException e) {
                Log.e("MapsActivity", "Location permission denied", e);
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupMockProvider() {
        try {
            if (!locationManager.isProviderEnabled(MOCK_PROVIDER)) {
                locationManager.addTestProvider(MOCK_PROVIDER, false, false, false, false, true,
                        true, true, ProviderProperties.POWER_USAGE_HIGH, ProviderProperties.ACCURACY_COARSE);
                locationManager.setTestProviderEnabled(MOCK_PROVIDER, true);
                Log.d("MapsActivity", "Mock provider added and enabled");
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "Mock location permission is required", Toast.LENGTH_SHORT).show();
        }
    }

    private void startMockMovement() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 이동 경로를 시뮬레이션하여 경도와 위도를 조금씩 변화
                latitude += 0.00001;  // 이동량 조정
                longitude += 0.00001;

                // 모의 위치 생성 및 설정
                Location mockLocation = new Location(MOCK_PROVIDER);
                mockLocation.setLatitude(latitude);
                mockLocation.setLongitude(longitude);
                mockLocation.setAccuracy(1.0f);
                mockLocation.setSpeed((float) MOCK_SPEED_M_S); // 시속 15km/h로 설정
                mockLocation.setTime(System.currentTimeMillis());
                mockLocation.setElapsedRealtimeNanos(System.nanoTime());  // 시스템 타임 추가

                try {
                    // 모의 위치 업데이트 전송
                    locationManager.setTestProviderLocation(MOCK_PROVIDER, mockLocation);
                    updateMockLocation(mockLocation.getLatitude(), mockLocation.getLongitude());
                    Log.d("MapsActivity", "Mock location updated: Lat=" + latitude + ", Lng=" + longitude);
                }  catch (SecurityException e) {
                    Log.e("MapsActivity", "SecurityException: Mock location permission not granted", e);
                } catch (IllegalArgumentException e) {
                    Log.e("MapsActivity", "IllegalArgumentException: Issue with mock location provider", e);
                }

                // 1초마다 업데이트
                handler.postDelayed(this, 1000);
            }
        }, 1000);
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
    protected void onDestroy() {
        super.onDestroy();
        try {
            locationManager.removeTestProvider(MOCK_PROVIDER);  // 사용한 테스트 프로바이더의 이름에 따라 변경
        } catch (SecurityException e) {
            e.printStackTrace();
            // 예외가 발생한 경우 Mock Location 권한이 없음을 의미
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


    // 여기서 부터 모의로 속도 구하는 함수
    private Location previousLocation;
    private long previousTime;

    private void updateMockLocation(double latitude, double longitude) {
        Location currentLocation = new Location(MOCK_PROVIDER);
        currentLocation.setLatitude(latitude);
        currentLocation.setLongitude(longitude);
        currentLocation.setTime(System.currentTimeMillis());

        // 이전 위치가 있다면 속도를 계산
        if (previousLocation != null) {
            float distance = previousLocation.distanceTo(currentLocation); // 거리(m)
            long timeDelta = currentLocation.getTime() - previousTime; // 시간 차이(ms)

            if (timeDelta > 0) {
                float speed = (distance / timeDelta) * 1000; // m/s 단위로 변환
                float speedKmH = speed * 3.6f; // km/h 단위로 변환
                Log.d("MapsActivity", "Calculated speed: " + speedKmH + " km/h");

                binding.speedTextView.setText(String.format(Locale.US, "Speed: %.2f km/h", speedKmH));
            }
        }

        // 현재 위치와 시간을 이전 위치로 저장
        previousLocation = currentLocation;
        previousTime = currentLocation.getTime();
    }
}