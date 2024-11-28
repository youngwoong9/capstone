package com.example.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetpackcompose.ui.theme.BikeMateTheme
import com.example.jetpackcompose.viewmodels.CourseViewModel

// 주행 화면
@Composable
fun CyclingScreen(
    navController: NavController,
    courseViewModel: CourseViewModel,
    courseId: String? = null
) {
    var isGhostMode by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (isGhostMode) {
                    // 고스트창
                    GhostWindow(modifier = Modifier.align(Alignment.TopEnd))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Button(
                    onClick = {navController.navigateUp()},
                    modifier = Modifier.weight(1f).padding(6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "뒤로가기")
                }

                Button(
                    onClick = { /** */ },
                    modifier = Modifier.weight(1f).padding(6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Run")
                }

                Button(
                    onClick = { isGhostMode = !isGhostMode },
                    modifier = Modifier.weight(1f).padding(6.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = if (isGhostMode) "실시간" else "고스트")
                }
            }
        }
    }
}

// 고스트 창
@Composable
fun GhostWindow(
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.border(1.dp, Color.Black).background(color = Color.White).padding(6.dp),
    ) {
        Text(text = "순위", fontWeight = FontWeight.ExtraBold)
        Text(text = "고스트1: +500m")
        Text(text = "고스트2: -10m")
        Text(text = "나", color = Color.Red)
        Text(text = "고스트3: -200m")
    }
}

@Preview(showBackground = true)
@Composable
fun CyclingPreview() {
    BikeMateTheme {
        CyclingScreen(
            navController = NavHostController(LocalContext.current),
            courseViewModel = CourseViewModel()
        )
    }
}