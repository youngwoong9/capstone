package com.example.bikemate.states

import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime
import java.util.UUID

data class CourseState(
    val id: String = UUID.randomUUID().toString(),          // 코스 아이디
    val title: String = "",                                 // 코스 제목
    val description: String = "",                           // 코스 설명
    val bookmark: Boolean = false,                          // 즐겨찾기
    val icon: ImageVector? = null,                          // 코스 사진
    val creationDate: LocalDateTime = LocalDateTime.now(),  // 코스 생성 시간
    val distance: Double = 0.0,                             // 코스 길이
    val radius: Double = 0.0,                               // 검색 위치의 반경

    val createdBy: String = "",
    val isPublic: Boolean = false,
)