package com.packcheng.owl.ui.course

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CourseDetails(
    courseId: Long,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    Text(text = "CourseDetail")
}