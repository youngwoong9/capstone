package com.example.bikemate.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bikemate.components.CircularIconTextButton
import com.example.bikemate.components.CourseItem
import com.example.bikemate.viewmodels.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseManagementScreen(
    navController: NavHostController,
    courseViewModel: CourseViewModel,
) {
    // 즐겨찾기된 코스들
    val bookmarkedCourseStates by courseViewModel.bookmarkedCourseStatesFlow.collectAsState()

    var selectedSortOption by rememberSaveable { mutableStateOf("최신순") } // 최신순 또는 날짜순 지정
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }       // 드롭다운메뉴 보여주기

    // 정렬된 즐겨찾기 코스들
    val sortedCourseStates = when (selectedSortOption) {
        "최신순" -> bookmarkedCourseStates.sortedByDescending { it.creationDate }
        "날짜순" -> bookmarkedCourseStates.sortedBy { it.creationDate }
        else -> bookmarkedCourseStates
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("코스 관리") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() } // 뒤로 가기
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CircularIconTextButton(
                        onClick = { /** */ },
                        icon = Icons.Outlined.Delete, text = "삭제"
                    )
                    CircularIconTextButton(
                        onClick = { navController.navigate("courseCreation") },
                        icon = Icons.Outlined.Add, text = "생성"
                    )
                    CircularIconTextButton(
                        onClick = { /** */ },
                        icon = Icons.Outlined.Edit, text = "수정"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color.LightGray),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "정렬 기준")
                Spacer(modifier = Modifier.width(8.dp)) // Space between text and button

                // 정렬옵션 드롭다운 메뉴
                SortOptionDropdown(
                    selectedSortOption = selectedSortOption,
                    onSortOptionSelected = { newOption ->
                        selectedSortOption = newOption
                    },
                    isDropdownMenuExpanded = isDropdownMenuExpanded,
                    onDropdownMenuDismiss = { isDropdownMenuExpanded = false },
                    onDropdownButtonClicked = { isDropdownMenuExpanded = true }
                )
            }

            if (bookmarkedCourseStates.isEmpty()) {
                Text("즐겨찾기한 코스가 없습니다.", modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(sortedCourseStates) { _, courseState ->
                        CourseItem(
                            navController = navController,
                            courseState = courseState,
                            onStarClick = {
                                courseViewModel.toggleBookmark(courseState)
                            },
                            showRecordButton = true,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

// 정렬옵션 드롭다운 메뉴
@Composable
fun SortOptionDropdown(
    selectedSortOption: String,
    onSortOptionSelected: (String) -> Unit,
    isDropdownMenuExpanded: Boolean,
    onDropdownMenuDismiss: () -> Unit,
    onDropdownButtonClicked: () -> Unit
) {
    Box {
        TextButton(
            onClick = onDropdownButtonClicked
        ) {
            Text(selectedSortOption) // Shows the current selection
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "정렬 옵션"
            )
        }

        // 정렬 옵션을 위한 드롭다운 메뉴
        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = onDropdownMenuDismiss,
        ) {
            DropdownMenuItem(
                onClick = {
                    onSortOptionSelected("최신순")
                    onDropdownMenuDismiss()
                },
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("최신순")
                    }
                }
            )

            HorizontalDivider(thickness = 2.dp, color = Color.Black)

            DropdownMenuItem(
                onClick = {
                    onSortOptionSelected("날짜순")
                    onDropdownMenuDismiss()
                },
                text = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("날짜순")
                    }
                }
            )
        }
    }
}