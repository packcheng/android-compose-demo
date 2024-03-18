package com.packcheng.owl.ui

import androidx.compose.runtime.Composable
import com.packcheng.owl.ui.theme.YellowTheme

@Composable
fun OwlApp(finishActivity: ()->Unit){
    YellowTheme {
        NavGraph(finishActivity = finishActivity)
    }
}