package com.packcheng.owl.ui.courses

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.packcheng.owl.model.Course

@Composable
fun MyCourses(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = "MyCourses")
}