package com.packcheng.owl.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.packcheng.owl.ui.MainDestinations.COURSES_ROUTE
import com.packcheng.owl.ui.MainDestinations.COURSE_DETAIL_ID_KEY
import com.packcheng.owl.ui.MainDestinations.COURSE_DETAIL_ROUTE
import com.packcheng.owl.ui.MainDestinations.ONBOARDING_ROUTE
import com.packcheng.owl.ui.course.CourseDetails
import com.packcheng.owl.ui.courses.CoursesTag
import com.packcheng.owl.ui.courses.courses
import com.packcheng.owl.ui.onboarding.Onboarding

/**
 * Destinations used in the ([OwlApp]).
 */
object MainDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val COURSES_ROUTE = "courses"
    const val COURSE_DETAIL_ROUTE = "course"
    const val COURSE_DETAIL_ID_KEY = "courseId"
}

@Composable
fun NavGraph(
    finishActivity: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = COURSES_ROUTE,
    showOnboardingInitially: Boolean = true,
    navController: NavHostController = rememberNavController()
) {

    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = ONBOARDING_ROUTE) {
            BackHandler {
                finishActivity()
            }
            Onboarding(onBoardingComplete = {
                // Set the flag so that onboarding is not shown next time.
                onboardingComplete.value = true
                actions.onboardingComplete()
            }
            )
        }

        navigation(
            startDestination = CoursesTag.FEATURED_COURSES.route,
            route = MainDestinations.COURSES_ROUTE
        ) {
            courses(
                onCourseSelect = { courseId, navBackStackEntry ->
                    actions.openCourse(
                        courseId,
                        navBackStackEntry
                    )
                },
                onboardingComplete = onboardingComplete,
                navController = navController,
                modifier = modifier
            )
        }

        composable(
            route = "${COURSE_DETAIL_ROUTE}/{$COURSE_DETAIL_ID_KEY}", arrayListOf(
                navArgument(COURSE_DETAIL_ID_KEY) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val currentCourseId = arguments.getLong(COURSE_DETAIL_ID_KEY)
            CourseDetails(
                courseId = currentCourseId,
                selectCourse = { courseId -> actions.openCourse(courseId, backStackEntry) },
                upPress = { actions.upPress(backStackEntry) })
        }
    }
}


/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onboardingComplete: () -> Unit = {
        navController.popBackStack()
    }

    // Used from COURSES_ROUTE
    val openCourse = { newCourseId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
        }
    }

    // Used from COURSE_DETAIL_ROUTE
    val relatedCourse = { newCourseId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
        }
    }

    // Used from COURSE_DETAIL_ROUTE
    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

