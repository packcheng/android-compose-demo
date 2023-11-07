package com.packcheng.demo.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.packcheng.demo.news.data.AppContainer
import com.packcheng.demo.news.ui.home.HomeRoute
import com.packcheng.demo.news.ui.home.HomeViewModel
import com.packcheng.demo.news.ui.interests.InterestsRoute
import com.packcheng.demo.news.ui.interests.InterestsViewModel

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/9/1 16:42
 */
@Composable
fun ZbcNewsNavGraph(
    appContainer: AppContainer,
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = ZbcNewsDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ZbcNewsDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel =
                viewModel(factory = HomeViewModel.provideFactory(appContainer.postsRepository))

            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }

        composable(ZbcNewsDestinations.INTERESTS_ROUTE) {
            val interestsViewModel: InterestsViewModel = viewModel(
                factory = InterestsViewModel.provideFactory(appContainer.interestsRepository)
            )

            InterestsRoute(
                interestsViewModel = interestsViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
    }
}