package com.example.capstonemap.polyLine;

import com.example.capstonemap.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RouteRepository {
    public void saveRoute(RouteDto routeDto, Long userId,Runnable onSuccess, Runnable onError) {
        ApiClient.getRouteApiService().saveRoute(routeDto, userId).enqueue(new Callback<Void>() {
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
