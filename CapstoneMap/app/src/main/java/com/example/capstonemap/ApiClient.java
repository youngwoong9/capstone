package com.example.capstonemap;

import com.example.capstonemap.bootApiTest.ApiService;
import com.example.capstonemap.service.RouteApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080"; // 로컬 서버 주소
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    //ApiClient 클래스의 ApiService를 쓴다.
    //api 인터페이스 객체이다.
    private static final ApiService apiService = ApiClient.getClient().create(ApiService.class);
    private static final RouteApiService routeApiService = ApiClient.getClient().create(RouteApiService.class);

    public static ApiService getApiService() {
        return apiService;
    }

    public static RouteApiService getRouteApiService() {
        return routeApiService;
    }
}