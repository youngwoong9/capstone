package com.example.bikemate.states

data class UserState(
    val nickname: String = "",
    val password: String = "",
    val courses: List<CourseState> = emptyList()
)