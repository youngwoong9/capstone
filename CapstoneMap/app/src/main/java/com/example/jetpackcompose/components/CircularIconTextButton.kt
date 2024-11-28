package com.example.jetpackcompose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CircularIconTextButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String,
    iconSize: Dp = 40.dp,
    fontSize: TextUnit = 15.sp,
    containerColor: Color = Color.Gray,
    contentColor: Color = Color.White,
    buttonSize: Dp = 80.dp
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        modifier = Modifier.size(buttonSize)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon, contentDescription = text,
                modifier = Modifier.size(iconSize)
            )
            Text(text = text, fontSize = fontSize)
        }
    }
}