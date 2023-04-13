package com.packcheng.composedemo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.packcheng.composedemo.ui.theme.ComposeDemoTheme

/**
 * 应用主页
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @since 2021/9/16 5:03 下午
 */
@Composable
fun AppScreen() {
    ProvideWindowInsets {
        ComposeDemoTheme {
            val navController = rememberNavController()
            Scaffold { innerPaddingModifier ->
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        }
    }
}