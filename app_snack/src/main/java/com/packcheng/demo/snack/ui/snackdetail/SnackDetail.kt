package com.packcheng.demo.snack.ui.snackdetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/29 15:48
 */

@Composable
fun SnackDetail(snackId: Long, upPressed: () -> Unit) {
    Text(text = "SnackDetail: $snackId")
}