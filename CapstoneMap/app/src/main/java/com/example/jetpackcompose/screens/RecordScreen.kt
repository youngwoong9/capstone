package com.example.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavHostController,
    courseId: String? = null
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("기록 관리") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() } // 뒤로 가기
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.LightGray),
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = "닉네임", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(text = "주행 기록", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Text(text = "상위(%)", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(20) { index ->
                    RecordItem(
                        nickname = "아무개${index + 1}",
                        record = "${9 + index}:${(10 + index) % 60}.265",
                        rankPercentage = "${(index + 1) * 1}%",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RecordItem(
    nickname: String = "아무개",
    record: String = "9:17.265",
    rankPercentage: String = "0.1%",
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(
                color = Color.Yellow,
                shape = CircleShape
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Nickname Column
        Text(
            text = nickname,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(thickness = 1.dp, color = Color.Black, modifier = Modifier.height(35.dp))

        // Riding Record Column
        Text(
            text = record,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        VerticalDivider(thickness = 1.dp, color = Color.Black, modifier = Modifier.height(35.dp))

        // Rank Percentage Column
        Text(
            text = rankPercentage,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}