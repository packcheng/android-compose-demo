package com.packcheng.owl.ui.courses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.packcheng.owl.R
import com.packcheng.owl.model.topics
import com.packcheng.owl.ui.MainDestinations

fun NavGraphBuilder.courses(
    onCourseSelect: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(CoursesTag.MY_COURSES.route) { from ->
        MyCourses(
            courses = com.packcheng.owl.model.courses,
            selectCourse = { courseId -> onCourseSelect(courseId, from) },
            modifier = modifier
        )
    }
    composable(CoursesTag.FEATURED_COURSES.route) { from ->
        LaunchedEffect(key1 = onboardingComplete) {
            if (!onboardingComplete.value) {
                navController.navigate(MainDestinations.ONBOARDING_ROUTE)
            }
        }
        if (onboardingComplete.value) {
            FeaturedCourses(
                courses = com.packcheng.owl.model.courses,
                selectCourse = { courseId -> onCourseSelect(courseId, from) },
                modifier = modifier
            )
        }
    }
    composable(CoursesTag.SEARCH_COURSES.route) {
        SearchCourses(topics = topics, modifier = modifier)
    }
}

@Composable
fun CoursesAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_lockup_white),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { /* todo */ }
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = stringResource(R.string.label_profile)
            )
        }
    }
}

enum class CoursesTag(
    @StringRes val tittle: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    MY_COURSES(R.string.my_courses, R.drawable.ic_grain, CoursesDestinations.MY_COURSES_ROUTE),
    FEATURED_COURSES(R.string.featured, R.drawable.ic_featured, CoursesDestinations.FEATURED_ROUTE),
    SEARCH_COURSES(R.string.search, R.drawable.ic_search, CoursesDestinations.SEARCH_COURSES_ROUTE)
}

/**
 * Destinations used in the ([OwlApp]).
 */
private object CoursesDestinations {
    const val FEATURED_ROUTE = "courses/featured"
    const val MY_COURSES_ROUTE = "courses/my"
    const val SEARCH_COURSES_ROUTE = "courses/search"
}