package com.example.capstonemap.locationUpdate;

import com.example.capstonemap.routes.RouteDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserRecordApiService {

    // @Body -> 객체를 보냄, @Query -> 값을 전달함, @Path -> url의 {} 부분에 값을 삽입함
    @POST("/api/users/{userId}/{routeId}/userUpdates")
    Call<Void> saveMyRecord(@Body UserRecordDto userRecordDto, @Path("userId") Long userId, @Path("routeId") Long routeId);

    // 자신의 기록을 가져옴
    @GET("/api/users/{userId}/{routeId}/myOldRecord")
    Call<UserRecordDto> getMyOldRecord(@Path("userId") Long userId, @Path("routeId") Long routeId);

    // 자신의 기록 가져오는 것과 통합할 수도 있지만 편의상 분리함
    @GET("/api/users/{userId}/{routeId}/oldRecord")
    Call<UserRecordDto> getOldRecord(@Path("userId") Long userId, @Path("routeId") Long routeId);
}
