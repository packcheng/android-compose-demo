package com.packcheng.demo.snack.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.packcheng.demo.snack.R
import com.packcheng.demo.snack.ui.components.ZbcBottomNavigation
import com.packcheng.demo.snack.ui.components.ZbcSnackSurface
import com.packcheng.demo.snack.ui.home.cart.Cart
import com.packcheng.demo.snack.ui.home.search.Search
import com.packcheng.demo.snack.ui.theme.JetsnackColors
import com.packcheng.demo.snack.ui.theme.ZbcSnackTheme

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/28 17:48
 */
fun NavGraphBuilder.addHomeGraph(
    onSnackSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.FEED.route) { from ->
        Feed(onSnackClick = { id -> onSnackSelected(id, from) }, modifier = modifier)
    }
    composable(HomeSections.SEARCH.route) { from ->
        Search(onSnackClick = { id -> onSnackSelected(id, from) }, modifier = modifier)
    }
    composable(HomeSections.CART.route) { from ->
        Cart(onSnackClick = { id -> onSnackSelected(id, from) }, modifier = modifier)
    }
    composable(HomeSections.PROFILE.route) {
        Profile(modifier = modifier)
    }
}


enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, "home/feed"),
    SEARCH(R.string.home_search, Icons.Outlined.Search, "home/search"),
    CART(R.string.home_cart, Icons.Outlined.ShoppingCart, "home/cart"),
    PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, "home/profile")
}


@Composable
fun ZbcSnackBottomBar(
    tabs: Array<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    color: Color = ZbcSnackTheme.colors.iconPrimary,
    contentColor: Color = ZbcSnackTheme.colors.iconInteractive
) {
    val routes = remember { tabs.map { it.route } }
    val currentSection = tabs.first { it.route == currentRoute }

    ZbcSnackSurface(
        color = color,
        contentColor = contentColor
    ) {
        ZbcBottomNavigation() {
            tabs.forEach { tab ->
                val tabColor =
                    if (tab.route == currentRoute) ZbcSnackTheme.colors.iconInteractive else ZbcSnackTheme.colors.iconInteractiveInactive
                BottomNavigationItem(
                    selected = tab.route == currentRoute,
                    onClick = { navigateToRoute(tab.route) },
                    label = {
                        Text(
                            text = tab.name,
                            color = tabColor
                        )
                    },
                    icon = {
                        Icon(
                            tab.icon,
                            contentDescription = tab.route,
                            tint = tabColor
                        )
                    })
            }
        }
    }
}