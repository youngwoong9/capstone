package com.example.capstonemap.polyLine;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouteApiService {
    // @Body -> 객체를 보냄, @Query -> 값을 전달함
    @POST("/api/routes")
    Call<Void> saveRoute(@Body RouteDto routeDto, @Query("userId") Long userId);

}
