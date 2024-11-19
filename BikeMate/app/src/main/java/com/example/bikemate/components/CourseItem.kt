package com.example.bikemate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bikemate.states.CourseState
import com.example.bikemate.ui.theme.BikeMateTheme

// 코스 정보를 보여주는 아이템
@Composable
fun CourseItem(
    navController: NavHostController? = null,
    courseState: CourseState,
    onStarClick: () -> Unit,
    showRecordButton: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color.LightGray)
            .border(1.dp, Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = courseState.icon ?: Icons.Default.Clear,
            contentDescription = "코스 그림",
            modifier = Modifier.size(50.dp),
            tint = Color.Magenta
        )


        Column(
            modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                onClick = { navController?.navigate("cycling/${courseState.id}") }
            )
        ) {
            Text(
                text = courseState.title, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider(thickness = 1.dp, color = Color.Black)
            Text(
                text = courseState.description, style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onStarClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "즐겨찾기",
                    modifier = Modifier.size(50.dp),
                    tint = if (courseState.bookmark) Color.Yellow else Color.Black
                )
            }

            if (showRecordButton) {
                TextButton(
                    onClick = { navController?.navigate("record/${courseState.id}") },
                ) {
                    Text(
                        text = "기록",
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BikeMateTheme {
    }
}