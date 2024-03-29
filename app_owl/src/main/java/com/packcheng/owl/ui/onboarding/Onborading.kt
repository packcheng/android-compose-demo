package com.packcheng.owl.ui.onboarding

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.packcheng.owl.R
import com.packcheng.owl.model.Topic
import com.packcheng.owl.model.topics
import com.packcheng.owl.ui.theme.OwlTheme
import com.packcheng.owl.ui.theme.YellowTheme
import com.packcheng.owl.ui.theme.pink500
import com.packcheng.owl.ui.utils.NetworkImage
import kotlin.math.max

@Composable
fun Onboarding(onBoardingComplete: () -> Unit) {
    YellowTheme {
        Scaffold(
            topBar = { AppBar() },
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
            },
            backgroundColor = MaterialTheme.colors.primarySurface
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(innerPadding)
            ) {
                Text(
                    text = stringResource(R.string.choose_topics_that_interest_you),
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 32.dp
                    )
                )
                TopicsGrid(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                )
                Spacer(Modifier.height(56.dp)) // center grid accounting for FAB
            }
        }
    }
}

@Composable
fun AppBar(modifier: Modifier = Modifier, onSettingClick: () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = OwlTheme.images.lockupLogo), contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        IconButton(onClick = onSettingClick, modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = stringResource(id = R.string.label_settings)
            )
        }
    }
}

@Composable
private fun TopicsGrid(modifier: Modifier = Modifier) {
    StaggeredGrid(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        topics.forEach { topic ->
            TopicChip(topic = topic)
        }
    }
}

private enum class SelectionState {
    Unselected, Selected
}

private class TopicChipTransition(
    cornerRadius: State<Dp>,
    selectedAlpha: State<Float>,
    checkScale: State<Float>
) {
    val cornerRadius by cornerRadius
    val selectedAlpha by selectedAlpha
    val checkScale by checkScale
}

@Composable
private fun topicChipTransition(chipSelected: Boolean): TopicChipTransition {
    val transition = updateTransition(
        targetState = if (chipSelected) SelectionState.Selected else SelectionState.Unselected,
        label = "updateTransition"
    )
    val cornerRadius = transition.animateDp(label = "cornerRadius") { state ->
        when (state) {
            SelectionState.Unselected -> 0.dp
            SelectionState.Selected -> 28.dp
        }
    }

    val selectedAlpha = transition.animateFloat(label = "selectedAlpha") { state ->
        when (state) {
            SelectionState.Unselected -> 0F
            SelectionState.Selected -> 0.8F
        }
    }
    val checkScale = transition.animateFloat(label = "checkScale") { state ->
        when (state) {
            SelectionState.Unselected -> 0.6F
            SelectionState.Selected -> 1F
        }
    }
    return remember(key1 = transition) {
        TopicChipTransition(cornerRadius, selectedAlpha, checkScale)
    }
}

@Composable
private fun TopicChip(topic: Topic) {
    val (selected, onSelected) = remember { mutableStateOf(false) }
    val topicChipTransitionState = topicChipTransition(chipSelected = selected)
    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = OwlTheme.elevations.card,
        shape = OwlTheme.shapes.medium.copy(topStart = CornerSize(topicChipTransitionState.cornerRadius))
    ) {
        Row(modifier = Modifier.toggleable(value = selected, onValueChange = onSelected)) {
            Box {
                NetworkImage(
                    url = topic.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 72.dp, height = 72.dp)
                        .aspectRatio(1F)
                )
                if (topicChipTransitionState.selectedAlpha > 0) {
                    Surface(
                        color = pink500.copy(alpha = topicChipTransitionState.selectedAlpha),
                        modifier = Modifier.matchParentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary.copy(
                                alpha = topicChipTransitionState.selectedAlpha
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .scale(topicChipTransitionState.checkScale)
                        )
                    }
                }
            }
            Column {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Icon(
                            painter = painterResource(R.drawable.ic_grain),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = topic.courses.toString(),
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(content = content, modifier = modifier) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 } // Keep track of the width of each row
        val rowHeights = IntArray(rows) { 0 } // Keep track of the height of each row

        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of each row
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(rowX[row], rowY[row])
                rowX[row] += placeable.width
            }
        }
    }
}

@Preview(name = "Onboarding")
@Composable
private fun OnboardingPreview() {
    YellowTheme {
        Onboarding(onBoardingComplete = {})
    }
}

@Preview(name = "AppBar")
@Composable
private fun AppBarPreview() {
    YellowTheme {
        AppBar()
    }
}

@Preview(name = "TopicChip")
@Composable
private fun TopicChipPreview() {
    YellowTheme {
        TopicChip(topics.first())
    }
}