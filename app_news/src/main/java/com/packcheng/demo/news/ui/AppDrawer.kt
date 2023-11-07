package com.packcheng.demo.news.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.packcheng.demo.news.R
import com.packcheng.demo.news.ui.theme.ZbcNewsTheme

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/9/1 16:41
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    currentDestination: String,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    navigateToInterests: () -> Unit = {},
    closeDrawer: () -> Unit = {}
) {
    // TODO 可以设置这个modifier的宽高设置抽屉的宽高
    ModalDrawerSheet(modifier) {
        JetNewsLogo(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.home_title)) },
            icon = { Icon(Icons.Filled.Home, null) },
            selected = ZbcNewsDestinations.HOME_ROUTE == currentDestination,
            onClick = { navigateToHome();closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text(stringResource(id = R.string.interests_title)) },
            icon = { Icon(Icons.Filled.ListAlt, null) },
            selected = ZbcNewsDestinations.INTERESTS_ROUTE == currentDestination,
            onClick = { navigateToInterests();closeDrawer() },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@Composable
private fun JetNewsLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            painterResource(R.drawable.ic_jetnews_logo),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.ic_jetnews_wordmark),
            contentDescription = stringResource(R.string.app_name),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    ZbcNewsTheme {
        AppDrawer(
            currentDestination = ZbcNewsDestinations.HOME_ROUTE,
            navigateToHome = {},
            navigateToInterests = {},
            closeDrawer = { }
        )
    }
}