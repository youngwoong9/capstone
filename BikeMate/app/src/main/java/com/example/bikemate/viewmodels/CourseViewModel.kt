package com.example.bikemate.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bikemate.states.CourseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class CourseViewModel: ViewModel() {
    private val _courseStatesFlow = MutableStateFlow(emptyList<CourseState>())
    val courseStatesFlow = _courseStatesFlow.asStateFlow()

    // 즐겨찾기된 코스 리스트
    // stateIn: 흐름을 변환할 때 초기 값을 직접 지정할 수 있다. 이 경우 초기 값은 emptyList()로 설정되어 bookmarkedCourseStatesFlow가 항상 빈 목록으로 시작되도록 한다.
    // viewModelScope: 흐름 범위가 ViewModel의 수명 주기로 지정되도록 한다.
    // SharingStarted.Lazily: 작업을 연기하고 데이터가 실제로 필요할 때만(즉, UI가 데이터 관찰을 시작할 때) 수집을 시작하려 한다.
    val bookmarkedCourseStatesFlow = _courseStatesFlow.map { list ->
        list.filter { it.bookmark }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    init {
        loadInitialCourses()
    }

    // 초기 코스들을 저장
    private fun loadInitialCourses() {
        _courseStatesFlow.value = listOf(
            CourseState(title = "입문자용 코스", description = "입문\n반경 500m", bookmark = false, icon = Icons.Default.Place, isPublic = true, createdBy = "test",
                creationDate = LocalDateTime.of(2024, 11, 1, 0, 0, 0), distance = 0.5, radius = 0.5),
            CourseState(title = "초급자용 코스", description = "초급\n반경 1km", bookmark = false, icon = Icons.Default.ShoppingCart, isPublic = true, createdBy = "test",
                creationDate = LocalDateTime.of(2024, 11, 2, 0, 0, 0), distance = 1.0, radius = 1.0),
            CourseState(title = "중급자용 코스", description = "중급\n반경 1.5km", bookmark = false, icon = Icons.Default.AccountCircle, isPublic = true, createdBy = "test2",
                creationDate = LocalDateTime.of(2024, 11, 3, 0, 0, 0), distance = 1.5, radius = 1.5),
            CourseState(title = "고급자용 코스", description = "고급\n반경 2km", bookmark = false, icon = Icons.Default.Call, isPublic = true, createdBy = "test2",
                creationDate = LocalDateTime.of(2024, 11, 4, 0,0, 0, 0), distance = 2.0, radius = 2.0),
        )
    }

    // 생성한 코스 추가
    fun addCourse(courseState: CourseState) {
        _courseStatesFlow.update { it + courseState }
    }

    // 즐겨찾기를 설정/해제하는 함수
    fun toggleBookmark(courseState: CourseState) {
        _courseStatesFlow.update { courseStates ->
            courseStates.map {
                if (it.id == courseState.id) it.copy(bookmark = !it.bookmark) else it
            }
        }
    }
}