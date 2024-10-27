package com.example.capstonemap.repository;

import com.example.capstonemap.ApiClient;
import com.example.capstonemap.bootApiTest.ApiService;
import com.example.capstonemap.dto.RouteDto;
import com.example.capstonemap.service.RouteApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteRepository {
    public void saveRoute(RouteDto routeDto, Runnable onSuccess, Runnable onError) {
        ApiClient.getRouteApiService().saveRoute(routeDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    onSuccess.run();
                    System.out.println("API 호출 성공");
                } else {
                    onError.run();
                    System.out.println("API 호출 실패, 응답 코드: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
                System.out.println("API 호출 실패, 오류: " + t.getMessage());
            }
        });
    }
}
