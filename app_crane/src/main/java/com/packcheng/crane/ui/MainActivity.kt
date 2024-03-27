package com.packcheng.crane.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.packcheng.crane.calendar.CalendarScreen
import com.packcheng.crane.detail.launchDetailsActivity
import com.packcheng.crane.home.MainScreen
import com.packcheng.crane.home.MainViewModel
import com.packcheng.crane.ui.theme.CraneTheme
import com.packcheng.crane.util.ZbcLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            CraneTheme {
                val widthSizeClass = calculateWindowSizeClass(activity = this).widthSizeClass

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Routes.Home.route) {
                    composable(Routes.Home.route) {
                        MainScreen(
                            widthSize = widthSizeClass,
                            onExploreItemClicked = {
                                launchDetailsActivity(
                                    context = this@MainActivity,
                                    item = it
                                )
                            },
                            onDateSelectionClicked = { navController.navigate(Routes.Calendar.route) },
                        )
                    }

                    composable(Routes.Calendar.route) {
                        val parentEntry = remember(it) {
                            navController.getBackStackEntry(Routes.Home.route)
                        }
                        val parentViewModel = hiltViewModel<MainViewModel>(
                            parentEntry
                        )
                        ZbcLog.i(TAG, "calendar mainViewModel: $parentViewModel")
                        CalendarScreen(onBackPressed = {
                            navController.popBackStack()
                        }, mainViewModel = parentViewModel)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Calendar : Routes("calendar")
}