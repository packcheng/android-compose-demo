package com.packcheng.composedemo.ui.main

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.packcheng.composedemo.ui.home.Home

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @since 2021/9/16 5:27 下午
 */
fun NavGraphBuilder.addMainGraph(
    modifier: Modifier = Modifier
) {
    composable(MainSections.HOME_ROUTE) {
        Home(modifier = modifier)
    }
}

object MainSections {
    const val HOME_ROUTE = "home"
}