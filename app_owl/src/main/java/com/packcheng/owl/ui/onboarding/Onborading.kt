package com.packcheng.owl.ui.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.packcheng.owl.R
import com.packcheng.owl.ui.theme.YellowTheme

@Composable
fun Onboarding(onBoardingComplete: () -> Unit) {
    YellowTheme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onBoardingComplete,
                    modifier = Modifier
                        .navigationBarsPadding()
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Explore,
                        contentDescription = stringResource(R.string.label_continue_to_courses)
                    )
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(innerPadding)
            ) {
                Text(text = "onboarding")
            }
        }
    }
}