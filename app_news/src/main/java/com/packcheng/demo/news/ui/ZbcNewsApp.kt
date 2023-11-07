package com.packcheng.demo.news.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.packcheng.demo.news.data.AppContainer
import com.packcheng.demo.news.ui.theme.ZbcNewsTheme
import kotlinx.coroutines.launch

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/9/1 16:43
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZbcNewsApp(appContainer: AppContainer, widthSizeClass: WindowWidthSizeClass) {
    ZbcNewsTheme {
        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            ZbcNavigationAction(navController)
        }

        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: ZbcNewsDestinations.HOME_ROUTE

        val isExpandedScreen = WindowWidthSizeClass.Expanded == widthSizeClass
        val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)

        ModalNavigationDrawer(
            drawerContent = {
                AppDrawer(currentDestination = currentRoute,
                    navigateToHome = navigationActions.navigateToHome,
                    navigateToInterests = navigationActions.navigateToInterests,
                    closeDrawer = { coroutineScope.launch { sizeAwareDrawerState.close() } })
            },
            drawerState = sizeAwareDrawerState,
            // Only enable opening the drawer via gestures if the screen is not expanded
            gesturesEnabled = !isExpandedScreen
        ) {
            Row {
//                if (isExpandedScreen) {
//                    AppNavRail(
//                        currentRoute = currentRoute,
//                        navigateToHome = navigationActions.navigateToHome,
//                        navigateToInterests = navigationActions.navigateToInterests,
//                    )
//                }
                ZbcNewsNavGraph(
                    appContainer = appContainer,
                    isExpandedScreen = isExpandedScreen,
                    navController = navController,
                    openDrawer = {
                        coroutineScope.launch { sizeAwareDrawerState.open() }
                    }
                )
            }
        }
    }
}

/**
 * Determine the drawer state to pass to the modal drawer.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}


/**
 * Determine the content padding to apply to the different screens of the app
 */
@Composable
fun rememberContentPaddingForScreen(
    additionalTop: Dp = 0.dp,
    excludeTop: Boolean = false
) =
    WindowInsets.systemBars
        .only(if (excludeTop) WindowInsetsSides.Bottom else WindowInsetsSides.Vertical)
        .add(WindowInsets(top = additionalTop))
        .asPaddingValues()

