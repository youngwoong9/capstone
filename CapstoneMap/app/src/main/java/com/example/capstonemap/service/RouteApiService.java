package com.example.capstonemap.service;

import com.example.capstonemap.dto.RouteDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RouteApiService {
    @POST("/api/routes")
    Call<Void> saveRoute(@Body RouteDto routeDto);
}
