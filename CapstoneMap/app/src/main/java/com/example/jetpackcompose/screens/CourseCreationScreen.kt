package com.example.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.jetpackcompose.states.CourseState
import com.example.jetpackcompose.ui.theme.BikeMateTheme
import com.example.jetpackcompose.viewmodels.CourseViewModel
import com.example.jetpackcompose.viewmodels.UserViewModel
import java.time.LocalDateTime


// 코스 생성 화면
@Composable
fun CourseCreationScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    courseViewModel: CourseViewModel
) {
    var selectedButton by rememberSaveable { mutableStateOf("") } // 선택된 버튼을 상위에서 관리
    var showDialog by rememberSaveable { mutableStateOf(false) }

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
                selectedButton = selectedButton,
                onSelectedButtonChange = { selectedButton = it },
                onEndPointClick = {},
                onCommentClick = {},
                onDetailsClick = {},
                onManualRouteClick = {},
                onCreateClick = { showDialog = true },
                onCancelClick = { navController.navigateUp() }
            )
        }

        // Dialog 표시
        if (showDialog) {
            ConfirmationDialog(
                onDismissRequest = { showDialog = false },
                onConfirm = {
                    courseViewModel.addCourse(it)
                    showDialog = false
                    navController.navigateUp() // Dialog 확인 후 뒤로 이동
                }
            )
        }
    }
}

// 코스 생성 제어창
@Composable
fun CourseCreationControls(
    selectedButton: String,
    onSelectedButtonChange: (String) -> Unit,
    onEndPointClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onDetailsClick: () -> Unit = {},
    onManualRouteClick: () -> Unit = {},
    onCreateClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
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
                    onSelectedButtonChange("끝점/경유지")
                    onEndPointClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.DateRange, // Example icon
                label = "주석",
                isSelected = selectedButton == "주석",
                onClick = {
                    onSelectedButtonChange("주석")
                    onCommentClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.Search,
                label = "상세보기",
                isSelected = selectedButton == "상세보기",
                onClick = {
                    onSelectedButtonChange("상세보기")
                    onDetailsClick()
                }
            )
            IconTextButton(
                icon = Icons.Default.AccountBox,
                label = "수동경로",
                isSelected = selectedButton == "수동경로",
                onClick = {
                    onSelectedButtonChange("수동경로")
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

// Dialog 컴포저블
@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (CourseState) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var distance by rememberSaveable { mutableStateOf("") }
    var radius by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "코스 정보 기입",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // 코스 제목 입력
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("코스 제목") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // 코스 설명 입력
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("코스 설명") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // 코스 거리 입력
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("코스 길이 (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // 코스 반경 입력
                OutlinedTextField(
                    value = radius,
                    onValueChange = { radius = it },
                    label = { Text("검색 반경 (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = onDismissRequest) {
                        Text("취소")
                    }
                    Button(
                        onClick = {
                            val newCourse = CourseState(
                                title = title,
                                description = description,
                                distance = distance.toDoubleOrNull() ?: 0.0,
                                radius = radius.toDoubleOrNull() ?: 0.0,
                                creationDate = LocalDateTime.now()
                            )
                            onConfirm(newCourse)
                        }
                    ) {
                        Text("확인")
                    }
                }
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
    }
}