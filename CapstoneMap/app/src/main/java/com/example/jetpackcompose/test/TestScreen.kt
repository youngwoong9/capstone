package com.example.jetpackcompose.test

import android.content.Intent
import android.view.LayoutInflater
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.capstonemap.MapsActivity
import com.example.capstonemap.R
import com.google.android.gms.maps.MapView

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun TestScreen(navController: NavHostController) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "xml 화면으로 전환하기(클릭)",
                        color = Color.Red,
                        modifier = Modifier.clickable {
                            // Intent는 안드로이드에서 액티비티 간에 화면 전환을 하거나, 특정 작업을 수행하도록 요청하는 메시지 객체
                            // Intent를 사용해서 다른 액티비티를 시작하거나, 서비스 시작, 데이터를 다른 앱으로 전달하는 등의 작업을 수행
                            // 이 코드에서는 Intent를 통해 MapsActivity라는 액티비티를 열려고 한다.
                            val intent = Intent(context, MapsActivity::class.java)

                            // context: LocalContext.current로 얻은 현재의 Context
                            // intent: MapsActivity로 이동하기 위한 Intent 객체
                            // startActivity(intent)를 호출하면, 안드로이드 시스템이 Intent를 해석하고, 그에 맞는 액티비티(MapsActivity)를 찾아 실행
                            // 이렇게 하면 현재 화면에서 MapsActivity로 화면이 전환.
                            context.startActivity(intent)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() } // 뒤로 가기
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "아래는 AndroidView로 가져온 xml 화면",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            AndroidView(
                modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue),
                // factory 람다에서 LayoutInflater를 통해 activity_maps.xml을 인플레이션하고, 생성된 View를 반환
                factory = { context ->
                    val vew = LayoutInflater.from(context).inflate(R.layout.activity_maps, null)

                    vew // 반환된 View는 AndroidView 내부에서 사용되어 화면에 표시
                }
            )
        }
    }
}

 */

@Composable
fun TestScreen(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue),
                // factory 람다에서 LayoutInflater를 통해 activity_maps.xml을 인플레이션하고, 생성된 View를 반환
                factory = { context ->
                    val vew = LayoutInflater.from(context).inflate(R.layout.activity_maps, null)

                    vew // 반환된 View는 AndroidView 내부에서 사용되어 화면에 표시
                }
            )
        }
    }
}
