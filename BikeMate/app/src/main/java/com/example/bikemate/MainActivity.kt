package com.example.bikemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bikemate.screens.CourseCreationScreen
import com.example.bikemate.screens.CourseManagementScreen
import com.example.bikemate.screens.CyclingScreen
import com.example.bikemate.screens.LoginScreen
import com.example.bikemate.screens.MainScreen
import com.example.bikemate.screens.RecordScreen
import com.example.bikemate.screens.RegisterScreen
import com.example.bikemate.ui.theme.BikeMateTheme
import com.example.bikemate.viewmodels.CourseViewModel
import com.example.bikemate.viewmodels.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BikeMateTheme {
                val navController = rememberNavController()

                val isLoggedIn = checkIfLoggedIn() // 로그인 유무 확인
                val startDestination = if (isLoggedIn) "main" else "login" // 로그인 유무에 따라 시작 스크린 지정

                // 코스 뷰모델 및 유저 뷰모델 초기화
                val courseViewModel: CourseViewModel = viewModel()
                val userViewModel: UserViewModel = viewModel()

                NavHost(navController = navController, startDestination = "main" ) {
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
                }
            }
        }
    }
}

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