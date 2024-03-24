package com.packcheng.owl.ui.course

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.PlaylistPlay
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.packcheng.owl.R
import com.packcheng.owl.model.Course
import com.packcheng.owl.model.CourseRepo
import com.packcheng.owl.model.Lesson
import com.packcheng.owl.model.LessonsRepo
import com.packcheng.owl.ui.common.CourseListItem
import com.packcheng.owl.ui.common.OutlinedAvatar
import com.packcheng.owl.ui.theme.BlueTheme
import com.packcheng.owl.ui.theme.PinkTheme
import com.packcheng.owl.ui.theme.pink500
import com.packcheng.owl.ui.utils.NetworkImage
import com.packcheng.owl.ui.utils.lerp
import com.packcheng.owl.ui.utils.scrim
import kotlinx.coroutines.launch
import java.util.Locale

private val FabSize = 56.dp
private const val ExpandedSheetAlpha = 0.96f

@Composable
fun CourseDetails(
    courseId: Long,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    // Simplified for the sample
    val course = remember(courseId) { CourseRepo.getCourse(courseId) }
    // TODO: Show error if course not found.
    CourseDetails(course, selectCourse, upPress)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CourseDetails(
    course: Course,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    PinkTheme {
        BoxWithConstraints {
            val sheetState = rememberSwipeableState(initialValue = SheetState.Closed)
            val fabSize = with(LocalDensity.current) { FabSize.toPx() }
            val dragRange = constraints.maxHeight - fabSize
            val scope = rememberCoroutineScope()

            BackHandler(enabled = sheetState.currentValue == SheetState.Open) {
                scope.launch {
                    sheetState.animateTo(SheetState.Closed)
                }
            }

            Box(
                modifier = Modifier.swipeable(
                    state = sheetState,
                    anchors = mapOf(0f to SheetState.Closed, -dragRange to SheetState.Open),
                    thresholds = { _, _ -> FractionalThreshold(0.5F) },
                    orientation = Orientation.Vertical
                )
            ) {
                val openFraction = if (sheetState.offset.value.isNaN()) {
                    0f
                } else {
                    -sheetState.offset.value / dragRange
                }.coerceIn(0f, 1f)
                CourseDescription(course, selectCourse, upPress)
                LessonsSheet(
                    course,
                    openFraction,
                    this@BoxWithConstraints.constraints.maxWidth.toFloat(),
                    this@BoxWithConstraints.constraints.maxHeight.toFloat()
                ) { state ->
                    scope.launch {
                        sheetState.animateTo(state)
                    }
                }
            }
        }
    }
}

@Composable
private fun CourseDescription(
    course: Course,
    selectCourse: (Long) -> Unit,
    upPress: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            item { CourseDescriptionHeader(course, upPress) }
            item { CourseDescriptionBody(course) }
            item { RelatedCourses(course.id, selectCourse) }
        }
    }
}

@Composable
private fun CourseDescriptionHeader(
    course: Course,
    upPress: () -> Unit
) {
    Box {
        NetworkImage(
            url = course.thumbUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentColor = Color.White, // always white as image has dark scrim
            modifier = Modifier.statusBarsPadding()
        ) {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.label_back)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        OutlinedAvatar(
            url = course.instructor,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 20.dp) // overlap bottom of image
        )
    }
}

@Composable
private fun CourseDescriptionBody(course: Course) {
    Text(
        text = course.subject.uppercase(Locale.getDefault()),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 36.dp,
                end = 16.dp,
                bottom = 16.dp
            )
    )
    Text(
        text = course.name,
        style = MaterialTheme.typography.h4,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = stringResource(id = R.string.course_desc),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
    Divider(modifier = Modifier.padding(16.dp))
    Text(
        text = stringResource(id = R.string.what_you_ll_need),
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = stringResource(id = R.string.needs),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 32.dp
                )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RelatedCourses(
    courseId: Long,
    selectCourse: (Long) -> Unit
) {
    val relatedCourses = remember(courseId) { CourseRepo.getRelated(courseId) }
    BlueTheme {
        Surface(
            color = MaterialTheme.colors.primarySurface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Text(
                    text = stringResource(id = R.string.you_ll_also_like),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 24.dp
                        )
                )
                LazyRow(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        bottom = 32.dp,
                        end = FabSize + 8.dp
                    )
                ) {
                    items(
                        items = relatedCourses,
                        key = { it.id }
                    ) { related ->
                        CourseListItem(
                            course = related,
                            onCourseSelect = { selectCourse(related.id) },
                            titleStyle = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(288.dp, 80.dp),
                            iconSize = 14.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LessonsSheet(
    course: Course,
    openFraction: Float,
    width: Float,
    height: Float,
    updateSheet: (SheetState) -> Unit
) {
    // Use the fraction that the sheet is open to drive the transformation from FAB -> Sheet
    val fabSize = with(LocalDensity.current) { FabSize.toPx() }
    val fabSheetHeight = fabSize + WindowInsets.systemBars.getBottom(LocalDensity.current)
    val offsetX = lerp(width - fabSize, 0f, 0F, 0.15F, openFraction)
    val offsetY = lerp(height - fabSheetHeight, 0f, openFraction)
    val tlCorner = lerp(fabSize, 0f, 0f, 0.15f, openFraction)
    val surfaceColor = lerp(
        startColor = pink500,
        endColor = MaterialTheme.colors.primarySurface.copy(alpha = ExpandedSheetAlpha),
        startFraction = 0f,
        endFraction = 0.3f,
        fraction = openFraction
    )
    Surface(color = surfaceColor,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface),
        shape = RoundedCornerShape(topStart = tlCorner),
        modifier = Modifier.graphicsLayer {
            translationX = offsetX
            translationY = offsetY
        }) {
        Lessons(course, openFraction, surfaceColor, updateSheet)
    }
}


@Composable
private fun Lessons(
    course: Course,
    openFraction: Float,
    surfaceColor: Color = MaterialTheme.colors.surface,
    updateSheet: (SheetState) -> Unit
) {
    val lessons: List<Lesson> = remember(course.id) { LessonsRepo.getLessons(course.id) }

    Box(modifier = Modifier.fillMaxWidth()) {
        // When sheet open, show a list of the lessons
        val lessonsAlpha = lerp(0f, 1f, 0.2f, 0.8f, openFraction)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = lessonsAlpha }
                .statusBarsPadding()
        ) {
            val scroll = rememberLazyListState()
            val appBarElevation by animateDpAsState(if (scroll.isScrolled) 4.dp else 0.dp)
            val appBarColor = if (appBarElevation > 0.dp) surfaceColor else Color.Transparent
            TopAppBar(
                backgroundColor = appBarColor,
                elevation = appBarElevation
            ) {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
                IconButton(
                    onClick = { updateSheet(SheetState.Closed) },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ExpandMore,
                        contentDescription = stringResource(R.string.label_collapse_lessons)
                    )
                }
            }
            LazyColumn(
                state = scroll,
                contentPadding = WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                    .asPaddingValues()
            ) {
                items(
                    items = lessons,
                    key = { it.title }
                ) { lesson ->
                    Lesson(lesson)
                    Divider(startIndent = 128.dp)
                }
            }
        }

        // When sheet closed, show the FAB
        val fabAlpha = lerp(1f, 0f, 0f, 0.15f, openFraction)
        Box(
            modifier = Modifier
                .size(FabSize)
                .padding(start = 16.dp, top = 8.dp) // visually center contents
                .graphicsLayer { alpha = fabAlpha }
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.Center),
                onClick = { updateSheet(SheetState.Open) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlaylistPlay,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(R.string.label_expand_lessons)
                )
            }
        }
    }
}

@Composable
private fun Lesson(lesson: Lesson) {
    Row(
        modifier = Modifier
            .clickable(onClick = { /* todo */ })
            .padding(vertical = 16.dp)
    ) {
        NetworkImage(
            url = lesson.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(112.dp, 64.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = lesson.length,
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
        Text(
            text = lesson.formattedStepNumber,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

private enum class SheetState { Open, Closed }

private val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0
