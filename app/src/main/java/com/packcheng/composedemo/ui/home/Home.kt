package com.packcheng.composedemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @since 2021/9/16 5:24 下午
 */
@Composable
fun Home(modifier: Modifier = Modifier) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color(0XFFFFB22E)), Alignment.Center) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "aaaa")
            Text(text = "aaaa")
            Text(text = "aaaa")
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}