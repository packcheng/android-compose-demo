package com.packcheng.owl.ui.courses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.OndemandVideo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.packcheng.owl.R
import com.packcheng.owl.model.Course
import com.packcheng.owl.model.courses
import com.packcheng.owl.ui.common.OutlinedAvatar
import com.packcheng.owl.ui.theme.BlueTheme
import com.packcheng.owl.ui.theme.OwlTheme
import com.packcheng.owl.ui.utils.NetworkImage
import java.util.Locale
import kotlin.math.ceil

@Composable
fun FeaturedCourses(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
    ) {
        CoursesAppBar()
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            courses.forEach { course ->
                FeaturedCourse(course, selectCourse)
            }
        }
    }
}


@Composable
fun FeaturedCourse(
    course: Course,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(4.dp),
        color = MaterialTheme.colors.surface,
        elevation = OwlTheme.elevations.card,
        shape = MaterialTheme.shapes.medium
    ) {
        val featuredString = stringResource(id = R.string.featured)
        ConstraintLayout(modifier = Modifier
            .clickable(
                onClick = { selectCourse(course.id) }
            )
            .semantics {
                contentDescription = featuredString
            }) {
            val (image, avatar, subject, name, steps, icon) = createRefs()
            NetworkImage(
                url = course.thumbUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(4f / 3f)
                    .constrainAs(image) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    }
            )
            val outlineColor = LocalElevationOverlay.current?.apply(
                color = MaterialTheme.colors.surface,
                elevation = OwlTheme.elevations.card
            ) ?: MaterialTheme.colors.surface
            OutlinedAvatar(
                url = course.instructor,
                outlineColor = outlineColor,
                modifier = Modifier
                    .size(38.dp)
                    .constrainAs(avatar) {
                        centerHorizontallyTo(parent)
                        centerAround(image.bottom)
                    }
            )
            Text(
                text = course.subject.uppercase(Locale.getDefault()),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.overline,
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(subject) {
                        centerHorizontallyTo(parent)
                        top.linkTo(avatar.bottom)
                    }
            )
            Text(
                text = course.name,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(name) {
                        centerHorizontallyTo(parent)
                        top.linkTo(subject.bottom)
                    }
            )
            val center = createGuidelineFromStart(0.5f)
            Icon(
                imageVector = Icons.Rounded.OndemandVideo,
                tint = MaterialTheme.colors.primary,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp)
                    .constrainAs(icon) {
                        end.linkTo(center)
                        centerVerticallyTo(steps)
                    }
            )
            Text(
                text = course.steps.toString(),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .constrainAs(steps) {
                        start.linkTo(center)
                        top.linkTo(name.bottom)
                    }
            )
        }
    }
}


@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(modifier = modifier, content = content) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }

        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val columnHeights = IntArray(columns) { 0 }

        val placeables = measurables.mapIndexed { index, measurable ->
            val column = index % columns
            val placeable = measurable.measure(itemConstraints)
            columnHeights[column] += placeable.height
            placeable
        }

        val height =
            columnHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
                ?: constraints.minHeight

        layout(constraints.maxWidth, height) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach() { placeable ->
                val column = shortestColumn(colY)
                placeable.place(column * columnWidth, colY[column])
                colY[column] += placeable.height
            }
        }
    }
}


private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}


@Preview(name = "Featured Course")
@Composable
private fun FeaturedCoursePreview() {
    BlueTheme {
        FeaturedCourse(
            course = courses.first(),
            selectCourse = { }
        )
    }
}

@Preview(name = "Featured Courses Portrait")
@Composable
private fun FeaturedCoursesPreview() {
    BlueTheme {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}

@Preview(name = "Featured Courses Dark")
@Composable
private fun FeaturedCoursesPreviewDark() {
    BlueTheme(darkTheme = true) {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}

@Preview(
    name = "Featured Courses Landscape",
    widthDp = 640,
    heightDp = 360
)
@Composable
private fun FeaturedCoursesPreviewLandscape() {
    BlueTheme {
        FeaturedCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}