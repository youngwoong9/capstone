package com.example.bikemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bikemate.components.CircularIconTextButton
import com.example.bikemate.components.CourseBottomSheet
import com.example.bikemate.components.SearchBar
import com.example.bikemate.components.FilterDialog
import com.example.bikemate.states.CourseState
import com.example.bikemate.viewmodels.CourseViewModel
import com.example.bikemate.viewmodels.UserViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    courseViewModel: CourseViewModel,
    userViewModel: UserViewModel
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }         // 필터 모달창 보여주기
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }    // 하단 시트 보여주기

    // 필터 옵션
    val filterOptions = rememberSavableFilterOptions()
    
    // 원본 코스들
    val courseStates by courseViewModel.courseStatesFlow.collectAsState()

    // 필터링된 코스들
    val filteredCourseStates = getFilteredCourses(courseStates, filterOptions)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.LightGray),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 검색바
            SearchBar(
                onShowDialog = { showDialog = true },
                onShowBottomSheet = { showBottomSheet = true }
            )

            // 코스관리 화면으로 이동
            CircularIconTextButton(
                onClick = { navController.navigate("courseManagement") },
                icon = Icons.Default.Add, text = "코스관리", fontSize = 14.sp, buttonSize = 100.dp
            )

            if(showDialog) {
                //필터 모달창
                FilterDialog(
                    // 모달창 외부를 탭할 시, 마지막으로 적용된 옵션으로 되돌아감
                    onDismissRequest = {
                        filterOptions.onCancelOptions()
                        showDialog = false
                    },
                    distanceOptions = filterOptions.distanceOptions,  // 거리 옵션
                    radiusOptions = filterOptions.radiusOptions,      // 반경 옵션
                    selectedDistanceOption = filterOptions.selectedDistanceOption,
                    selectedRadiusOption = filterOptions.selectedRadiusOption,
                    onDistanceOptionSelected = filterOptions.onDistanceOptionChange,
                    onRadiusOptionSelected = filterOptions.onRadiusOptionChange,
                    // 적용 시, 마지막으로 적용된 옵션을 업데이트
                    onApply = {
                        filterOptions.onApplyOptions()
                        showDialog = false
                    },
                    // 취소 시, 마지막으로 적용된 옵션으로 되돌아감
                    onCancel = {
                        filterOptions.onCancelOptions()
                        showDialog = false
                    }
                )
            }

            if (showBottomSheet) {
                // 코스 하단 시트
                CourseBottomSheet(
                    courseViewModel = courseViewModel,
                    navController = navController,
                    filteredCourseStates = filteredCourseStates,
                    onDismissRequest = { showBottomSheet = false },
                )
            }
        }
    }
}

// 필터옵션 클래스
data class FilterOptions(
    val distanceOptions: List<Pair<String, Double>>,
    val radiusOptions: List<Pair<String, Double>>,
    val selectedDistanceOption: String,
    val selectedRadiusOption: String,
    val lastAppliedDistanceOption: String,
    val lastAppliedRadiusOption: String,
    val onDistanceOptionChange: (String) -> Unit,
    val onRadiusOptionChange: (String) -> Unit,
    val onApplyOptions: () -> Unit,
    val onCancelOptions: () -> Unit,
) {
    val maxDistance: Double
        get() = distanceOptions.find { it.first == lastAppliedDistanceOption }?.second
            ?: Double.MAX_VALUE

    val maxRadius: Double
        get() = radiusOptions.find { it.first == lastAppliedRadiusOption }?.second
            ?: Double.MAX_VALUE
}

// 필터 옵션을 세팅하는 함수
@Composable
fun rememberSavableFilterOptions(): FilterOptions {
    val distanceOptions = listOf(
        "입문 (~500m)" to 0.5,
        "초급 (~1km)" to 1.0,
        "중급 (~1.5km)" to 1.5,
        "고급 (1.5km~)" to Double.MAX_VALUE
    )

    val radiusOptions = listOf(
        "반경 500m 이내" to 0.5,
        "반경 1km 이내" to 1.0,
        "반경 1.5km 이내" to 1.5,
        "반경 2km 이내" to 2.0
    )

    var selectedDistanceOption by rememberSaveable { mutableStateOf("") }
    var selectedRadiusOption by rememberSaveable { mutableStateOf("") }
    var lastAppliedDistanceOption by rememberSaveable { mutableStateOf("") }
    var lastAppliedRadiusOption by rememberSaveable { mutableStateOf("") }

    return FilterOptions(
        distanceOptions = distanceOptions,
        radiusOptions = radiusOptions,
        selectedDistanceOption = selectedDistanceOption,
        selectedRadiusOption = selectedRadiusOption,
        lastAppliedDistanceOption = lastAppliedDistanceOption,
        lastAppliedRadiusOption = lastAppliedRadiusOption,
        onDistanceOptionChange = { selectedDistanceOption = it },
        onRadiusOptionChange = { selectedRadiusOption = it },
        onApplyOptions = {
            lastAppliedDistanceOption = selectedDistanceOption
            lastAppliedRadiusOption = selectedRadiusOption
        },
        onCancelOptions = {
            selectedDistanceOption = lastAppliedDistanceOption
            selectedRadiusOption = lastAppliedRadiusOption
        }
    )
}

// 필터링된 코스들을 반환
fun getFilteredCourses(courseStates: List<CourseState>, filterOptions: FilterOptions): List<CourseState> {
    return courseStates.filter {
        it.distance <= filterOptions.maxDistance && it.radius <= filterOptions.maxRadius
    }
}

