package com.packcheng.composedemo.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.packcheng.composedemo.ui.main.MainSections
import com.packcheng.composedemo.ui.main.addMainGraph

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @since 2021/9/16 5:04 下午
 */
object MainDestinations {
    const val MAIN_ROUTE = "main"
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.MAIN_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        navigation(
            route = MainDestinations.MAIN_ROUTE,
            startDestination = MainSections.HOME_ROUTE
        ) {
            addMainGraph(modifier)
        }
    }
}