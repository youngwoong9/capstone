package com.example.capstonemap.locationUpdate;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.capstonemap.MapsActivity;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

public class GeofenceHelper extends MapsActivity {

    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent;
    private Context context;

    public GeofenceHelper(Context context) {
        this.context = context;
        geofencingClient = LocationServices.getGeofencingClient(context);
    }

    // 지오펜스 설정 메서드
    public void addGeofence(double latitude, double longitude, float radius, String geofenceId) {
        Geofence geofence = new Geofence.Builder()
                .setRequestId(geofenceId)
                .setCircularRegion(latitude, longitude, radius) // 지오펜스 위치와 반경 설정
                .setExpirationDuration(Geofence.NEVER_EXPIRE) // 만료 시간 설정
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT) // 진입과 이탈 감지
                .build();

        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();

        geofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent())
                .addOnSuccessListener(aVoid -> {
                    // 지오펜스 추가 성공
                    System.out.println("Geofence added successfully");
                })
                .addOnFailureListener(e -> {
                    // 지오펜스 추가 실패
                    System.out.println("Geofence failed to add: " + e.getMessage());
                });
    }

    // 지오펜스 이벤트를 처리할 PendingIntent 생성
    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(context, CourseInOut.class);
        geofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }
}