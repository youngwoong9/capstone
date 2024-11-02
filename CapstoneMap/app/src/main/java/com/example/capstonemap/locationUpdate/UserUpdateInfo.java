package com.example.capstonemap.locationUpdate;


import android.location.Location;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

// 유저의 정보중 어떤 부분을 업데이트할지 정함(속도나 위도 경도 등)
public class UserUpdateInfo {
    private boolean isInCourse = false;
    private UserUpdateDto userUpdateDto = new UserUpdateDto();
    private CourseInOut courseInOut;

    public UserUpdateInfo(CourseInOut courseInOut) {
        this.courseInOut = courseInOut;
    }

    public void updateUserInfo(double latitude, double longitude, float speed) {
        // DTO 업데이트 및 평균 속도 계산
        updateUserDto(latitude, longitude, speed);
        updateAverageSpeed();
    }

    private void updateAverageSpeed() {
        double totalSpeed = 0;
        if (userUpdateDto.getSpeedList().isEmpty()) {
            userUpdateDto.setAverageSpeed(0);
        } else {
            for (double s : userUpdateDto.getSpeedList()) {
                totalSpeed += s;
            }
            double averageSpeed = totalSpeed / userUpdateDto.getSpeedList().size();
            userUpdateDto.setAverageSpeed(averageSpeed);
        }
    }

    public void enterCourse() {
        isInCourse = true;
        userUpdateDto.setStartedTime(System.currentTimeMillis());
    }

    public void exitCourse() {
        isInCourse = false;
        userUpdateDto.setElapsedTime((System.currentTimeMillis() - userUpdateDto.getStartedTime()) / 1000);
    }

    private void updateUserDto(double latitude, double longitude, float speed) {
        userUpdateDto.setLatitude(latitude);
        userUpdateDto.setLongitude(longitude);
        userUpdateDto.setSpeed(speed);
    }
}