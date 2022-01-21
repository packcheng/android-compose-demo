package com.packcheng.composedemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.packcheng.composedemo.AppViewModel
import com.packcheng.composedemo.ui.theme.ComposeDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val appViewModel:AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
//            ComposeDemoTheme {
//                val systemUiController = rememberSystemUiController()
//                val darkIcons = MaterialTheme.colors.isLight
//                SideEffect {
//                    systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = darkIcons)
//                }
//
//                ScaffoldDemo()
//            }
            AppScreen()
        }
    }
}

@Composable
fun ScaffoldDemo() {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,

        // 抽屉
        drawerContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "抽屉内容")
            }
        },

        // 标题
        topBar = {
            TopAppBar(
                title = { Text(text = "tittle") },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                    }
                }
            )
        },

        //悬浮按钮
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("悬浮按钮") },
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("点击了悬浮按钮")
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,

        // 自定义SnackBar背景
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },

        //屏幕内容区域
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "屏幕内容区域")
            }
        },
    )
}