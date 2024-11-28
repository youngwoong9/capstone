package com.example.jetpackcompose.states

data class UserState(
    val nickname: String = "",
    val password: String = "",
    val myCourses: List<CourseState> = emptyList(),
    val bookMarkedCourses: List<CourseState> = emptyList()
)