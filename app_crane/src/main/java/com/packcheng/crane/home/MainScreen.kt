package com.packcheng.crane.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.packcheng.crane.util.ZbcLog

@Composable
fun MainScreen(
    widthSize: WindowWidthSizeClass,
    onExploreItemClicked: OnExploreItemClicked,
    onDateSelectionClicked: () -> Unit,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    ZbcLog.i("MainActivity-MainScreen", "home mainViewModel: $mainViewModel")
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Button(onClick = onDateSelectionClicked) {
                Text(text = "Click Me")
            }
        }

    }
}