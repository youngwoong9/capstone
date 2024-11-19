package com.example.bikemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bikemate.ui.theme.BikeMateTheme


// 코스 생성 화면
@Composable
fun CourseCreationScreen(
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Bottom
        ) {
            // 코스 생성 제어창
            CourseCreationControls(
                onEndPointClick = {},
                onCommentClick = {},
                onDetailsClick = {},
                onManualRouteClick = {},
                onCreateClick = {},
                onCancelClick = { navController.navigateUp() }
            )
        }
    }
}

// 코스 생성 제어창
@Composable
fun CourseCreationControls(
    onEndPointClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {},
    onManualRouteClick: () -> Unit = {},
    onCreateClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {

    var selectedButton by rememberSaveable { mutableStateOf("") } // 선택된 버튼 눈에 띄게 하기

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = Color.Gray).padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            IconTextButton(
                icon = Icons.Default.Favorite, // Example icon
                label = "끝점/경유지",
                isSelected = selectedButton == "끝점/경유지",

                onClick = {
                    selectedButton = "끝점/경유지"
                    onEndPointClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.DateRange, // Example icon
                label = "주석",
                isSelected = selectedButton == "주석",
                onClick = {
                    selectedButton = "주석"
                    onCommentClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.Search,
                label = "상세보기",
                isSelected = selectedButton == "상세보기",
                onClick = {
                    selectedButton = "상세보기"
                    onDetailsClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.AccountBox,
                label = "수동경로",
                isSelected = selectedButton == "수동경로",
                onClick = {
                    selectedButton = "수동경로"
                    onManualRouteClick()
                }
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onCreateClick,
                modifier = Modifier.weight(1f).padding(end = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("완료", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f).padding(start = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("취소", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun IconTextButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 8.dp),
            tint = if (isSelected) Color.Red else Color.White
        )
        Text(text = label,
            color = if (isSelected) Color.Red else Color.White,
            fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BikeMateTheme {
        CourseCreationScreen(navController = NavHostController(LocalContext.current))
    }
}