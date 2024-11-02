package com.example.capstonemap.locationUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.maps.OnMapReadyCallback;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

//지오펜서 코스 진입, 나감 이벤트를 받는다
//지오펜서 리스너 -> 코스 진입했을때 나갔을때의 동작을 하는 함수를 받는 인터페이스

public class CourseInOut extends BroadcastReceiver {
    private GeoFenceListener geoFenceListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            System.out.println("Geofencing error: " + geofencingEvent.getErrorCode());
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            geoFenceListener.onGeofenceEnter();
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            geoFenceListener.onGeofenceExit();
        }
    }

    public CourseInOut(GeoFenceListener geoFenceListener) {
        this.geoFenceListener = geoFenceListener;
    }
}