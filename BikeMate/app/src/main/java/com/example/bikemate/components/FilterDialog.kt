package com.example.bikemate.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// 필터 다이어로그(거리, 반경)
@Composable
fun FilterDialog(
    onDismissRequest: () -> Unit,
    distanceOptions: List<Pair<String, Double>>,
    radiusOptions: List<Pair<String, Double>>,
    selectedDistanceOption: String,
    selectedRadiusOption: String,
    onDistanceOptionSelected: (String) -> Unit,
    onRadiusOptionSelected: (String) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
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
                Text(text = "필터 옵션", fontSize = 18.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                // 필터 옵션
                Row {
                    // 거리 옵션
                    Column(modifier = Modifier.weight(1f)) {
                        distanceOptions.forEach { option ->
                            TextButton(
                                onClick = { onDistanceOptionSelected(option.first) },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (selectedDistanceOption == option.first) Color.Red else Color.Black
                                )
                            ) {
                                Text(text = option.first)
                            }
                        }
                    }

                    // 반경 옵션
                    Column(modifier = Modifier.weight(1f)) {
                        radiusOptions.forEach { option ->
                            TextButton(
                                onClick = { onRadiusOptionSelected(option.first) },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = if (selectedRadiusOption == option.first) Color.Red else Color.Black
                                )
                            ) {
                                Text(text = option.first)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 취소 버튼
                    Button(
                        onClick = onCancel,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        )
                    ) {
                        Text(text = "취소", fontWeight = FontWeight.Bold)
                    }

                    // 적용 버튼
                    Button(
                        onClick = onApply,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Blue
                        )
                    ) {
                        Text(text = "적용", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}