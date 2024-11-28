package com.example.jetpackcompose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.capstonemap.MapsActivity
import com.example.capstonemap.R
import com.example.capstonemap.databinding.ActivityMapsBinding
import com.example.jetpackcompose.screens.CourseCreationScreen
import com.example.jetpackcompose.screens.CourseManagementScreen
import com.example.jetpackcompose.screens.CyclingScreen
import com.example.jetpackcompose.screens.LoginScreen
import com.example.jetpackcompose.screens.MainScreen
import com.example.jetpackcompose.screens.RecordScreen
import com.example.jetpackcompose.screens.RegisterScreen
import com.example.jetpackcompose.test.TestScreen
import com.example.jetpackcompose.ui.theme.BikeMateTheme
import com.example.jetpackcompose.viewmodels.CourseViewModel
import com.example.jetpackcompose.viewmodels.UserViewModel
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        val composeView = findViewById<ComposeView>(R.id.composeView)

        composeView.setContent {
            Column {
                Text(text = "시작")

                // Compose 내에서 XML을 삽입 불가능?

                Text(text = "끝")
            }
        }

//        setContent {
//            BikeMateTheme {
//                TestScreen(navController = rememberNavController())
//            }
//        }
    }
}

fun setMyComposableContent(activity: Activity) {
    val composeView: ComposeView = activity.findViewById(R.id.composeView)

    composeView.setContent {
        Column {
            Text(text = "시작")

            // Compose 내에서 XML을 삽입 불가능?

            Text(text = "끝")
        }
    }
}


//        setContentView(R.layout.activity_maps)
//
//        val composeView = findViewById<ComposeView>(R.id.composeView)
//
//// Set the composable content to the ComposeView
//        composeView.setContent {
//            MyComposable()
//        }

/*
 val navController = rememberNavController()

 val isLoggedIn = checkIfLoggedIn() // 로그인 유무 확인
 val startDestination = if (isLoggedIn) "main" else "login" // 로그인 유무에 따라 시작 스크린 지정

 // 코스 뷰모델 및 유저 뷰모델 초기화
 val courseViewModel: CourseViewModel = viewModel()
 val userViewModel: UserViewModel = viewModel()

 val context = LocalContext.current

  */

/*
NavHost(navController = navController, startDestination = startDestination ) {
    // 로그인 화면
    composable("login") {
        LoginScreen(navController, userViewModel)
    }
    // 회원가입 화면
    composable("register") {
        RegisterScreen(navController, userViewModel)
    }
    // 메인 화면
    composable("main") {
        MainScreen(navController, courseViewModel, userViewModel)
    }
    // 코스관리 화면
    composable("courseManagement") {
        CourseManagementScreen(navController, courseViewModel, userViewModel)
    }
    // 주행 화면
    composable("cycling/{courseId}") { backStackEntry ->
        val courseId = backStackEntry.arguments?.getString("courseId")
        CyclingScreen(navController, courseViewModel, courseId)
    }
    // 기록 화면
    composable("record/{courseId}") { backStackEntry ->
        val courseId = backStackEntry.arguments?.getString("courseId")
        RecordScreen(navController, courseId)
    }
    // 코스 생성 화면
    composable("courseCreation") {
        CourseCreationScreen(navController, userViewModel, courseViewModel)
    }
    // compose <--> xml 테스트 화면
    composable("test") {
        TestScreen(navController)
    }
}

 */





private fun checkIfLoggedIn(): Boolean {
    // Replace with your actual logic to determine if the user is logged in
    return false
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BikeMateTheme {
    }
}

//            AndroidView(
//                modifier = Modifier.fillMaxSize().border(1.dp, Color.Blue),
//                // factory 람다에서 LayoutInflater를 통해 activity_maps.xml을 인플레이션하고, 생성된 View를 반환
//                factory = { context ->
//                    val vew = LayoutInflater.from(context).inflate(R.layout.activity_maps, null)
//
//                    vew // 반환된 View는 AndroidView 내부에서 사용되어 화면에 표시
//                }
//            )

//            AndroidView(
//                modifier = Modifier.fillMaxSize(),
//                factory = { context ->
//                    val fragmentContainer = FragmentContainerView(context).apply {
//                        id = R.id.map
//                    }
//
//                    // Add the SupportMapFragment dynamically
//                    val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
//                    fragmentTransaction.replace(R.id.map, SupportMapFragment.newInstance()).commit()
//
//                    fragmentContainer
//                }
//            )

//            AndroidView(
//                factory = {
//                    mapView.apply {
//                        onCreate(Bundle())
//                        getMapAsync { googleMap ->
//                            // Enable zoom controls
//                            googleMap.uiSettings.isZoomControlsEnabled = true
//
//                            // Enable the current location button
//                            googleMap.uiSettings.isMyLocationButtonEnabled = true
//
//                            // Additional map setup if necessary
//                        }
//                    }
//                },
//                update = { view ->
//                    view.onResume()
//                }
//            )