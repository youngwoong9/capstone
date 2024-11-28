package com.example.jetpackcompose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jetpackcompose.states.CourseState
import com.example.jetpackcompose.ui.theme.BikeMateTheme
import com.example.jetpackcompose.viewmodels.CourseViewModel


// 코스 하단시트
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseBottomSheet(
    navController: NavHostController,
    courseViewModel: CourseViewModel = viewModel(),
    filteredCourseStates: List<CourseState>,
    onDismissRequest: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.5f), // 버그? 있으므로 0.5f 초과하지 말 것
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(filteredCourseStates) { _, courseState ->
                CourseItem(
                    navController = navController,
                    courseState = courseState,
                    onStarClick = { courseViewModel.toggleBookmark(courseState) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CourseListPreview() {
    BikeMateTheme {
    }
}