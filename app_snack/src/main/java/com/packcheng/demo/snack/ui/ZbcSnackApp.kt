package com.packcheng.demo.snack.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.packcheng.demo.snack.ui.components.ZbcSnackScaffold
import com.packcheng.demo.snack.ui.components.ZbcSnackbar
import com.packcheng.demo.snack.ui.home.HomeSections
import com.packcheng.demo.snack.ui.home.ZbcSnackBottomBar
import com.packcheng.demo.snack.ui.home.addHomeGraph
import com.packcheng.demo.snack.ui.snackdetail.SnackDetail
import com.packcheng.demo.snack.ui.theme.ZbcSnackTheme

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/28 17:05
 */
@Composable
fun ZbcSnackApp() {
    ZbcSnackTheme {
        val appState = rememberZbcSnackAppState()
        ZbcSnackScaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    ZbcSnackBottomBar(
                        tabs = appState.bottomBarTabs,
                        currentRoute = appState.currentRoute!!,
                        navigateToRoute = appState::navigateToBottomBarRoute
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = it,
                    modifier = Modifier.systemBarsPadding(),
                    snackbar = { snackbarData -> ZbcSnackbar(snackbarData = snackbarData) })
            },
            scaffoldState = appState.scaffoldState
        ) { innerPadding ->
            NavHost(
                navController = appState.navController,
                startDestination = MainDestinations.HOME_ROUTE,
                modifier = Modifier.padding(innerPadding)
            ) {
                zbcSnackGraph(
                    onSnackSelected = appState::navigateToSnackDetail,
                    upPressed = appState::upPress
                )
            }
        }
    }
}

private fun NavGraphBuilder.zbcSnackGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    upPressed: () -> Unit
) {
    navigation(
        route = MainDestinations.HOME_ROUTE,
        startDestination = HomeSections.FEED.route
    ) {
        addHomeGraph(onSnackSelected)
    }

    composable(
        "${MainDestinations.SNACK_DETAIL_ROUTE}/{${MainDestinations.SNACK_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.SNACK_ID_KEY) { type = NavType.LongType })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val snackId = arguments.getLong(MainDestinations.SNACK_ID_KEY)
        SnackDetail(snackId = snackId, upPressed = upPressed)
    }
}