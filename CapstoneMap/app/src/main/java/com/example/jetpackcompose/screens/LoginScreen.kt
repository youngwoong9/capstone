package com.example.jetpackcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcompose.components.InputField
import com.example.jetpackcompose.ui.theme.BikeMateTheme
import com.example.jetpackcompose.viewmodels.UserViewModel

// 로그인 화면
@Composable
fun LoginScreen(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    // LocalContext.current: Jetpack Compose에서 현재 Context 객체를 가져오는 방법
    // Context는 안드로이드 앱의 전역적인 정보를 담고 있는 객체로, 앱 리소스에 접근하거나, 액티비티 시작, 토스트 메시지 표시 등 다양한 기능을 수행할 때 필요.
    val context =  LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("BikeMate", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                value = nickname,
                onValueChange = { nickname = it },
                label = "닉네임"
            )
            Spacer(modifier = Modifier.height(8.dp))

            InputField(
                value = password,
                onValueChange = { password = it },
                label = "비밀번호",
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    when {
                        nickname.isBlank() -> errorMessage = "닉네임을 입력해주세요."

                        password.isBlank() -> errorMessage = "비밀번호를 입력해주세요."

                        userViewModel.loginUser(
                            nickname,
                            password
                        ) -> navController.navigate("main")

                        else -> errorMessage = "가입되지 않은 회원입니다."

                    }
                }
            ) {
                Text("로그인")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.height(20.dp)) {
                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = Color.Red)
                }
            }

            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text("회원가입하기")
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                navController.navigate("test")
            }) {
                Text(
                    text = "xml 테스트 화면으로 이동",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    BikeMateTheme {
        LoginScreen(rememberNavController())
    }
}