package com.example.capstonemap.bootApi;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.capstonemap.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        textView = findViewById(R.id.textView);

        // Retrofit 설정 및 API 호출
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<String> call = apiService.getGreeting();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    textView.setText(response.body());
                } else {
                    textView.setText("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                textView.setText("Failed: " + t.getMessage());
                Log.e("MainActivity", t.getMessage());
            }
        });
    }
}