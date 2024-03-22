package com.packcheng.owl.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.packcheng.owl.ui.courses.CoursesTag
import com.packcheng.owl.ui.theme.BlueTheme

@Composable
fun OwlApp(finishActivity: () -> Unit) {
    BlueTheme {
        val navController = rememberNavController()
        val tags = remember {
            CoursesTag.values()
        }

        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
            bottomBar = { OwlBottomBar(navControl = navController, tabs = tags) }
        ) { innerPadding ->
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OwlBottomBar(navControl: NavHostController, tabs: Array<CoursesTag>) {
    val navBackStackEntry by navControl.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: CoursesTag.FEATURED_COURSES.route

    val routes = remember { CoursesTag.values().map { it.route }.toList() }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(
                    WindowInsets(bottom = 56.dp)
                )
            )
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (currentRoute != tab.route) {
                            navControl.navigate(tab.route) {
                                popUpTo(navControl.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { Text(text = stringResource(id = tab.tittle)) },
                    icon = {
                        Icon(
                            painter = painterResource(id = tab.icon),
                            contentDescription = null
                        )
                    },
                    alwaysShowLabel = false,
                    unselectedContentColor = LocalContentColor.current,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }

}