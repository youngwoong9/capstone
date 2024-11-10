package com.example.capstonemap.routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RouteApiService {
    // @Body -> 객체를 보냄, @Query -> 값을 전달함, @Path -> url의 {} 부분에 값을 삽입함
    @POST("/api/users/{userId}/routes")
    Call<Void> saveRoute(@Body RouteDto routeDto, @Path("userId") Long userId);

    @GET("/api/routes")
    Call<List<RouteDto>> getAllRoutes();
}
