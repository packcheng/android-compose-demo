package com.packcheng.owl.ui.courses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.packcheng.owl.R
import com.packcheng.owl.model.Topic
import com.packcheng.owl.model.topics
import com.packcheng.owl.ui.theme.YellowTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCourses(
    topics: List<Topic>,
    modifier: Modifier = Modifier
) {

    val (searchTerm, updateSearchTerm) = remember {
        mutableStateOf(TextFieldValue(""))
    }

    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        item { SearchTopBar(searchTerm, updateSearchTerm) }
        val filteredTopics = getTopics(searchTerm.text, topics)
        items(filteredTopics, key = { it.name }) {
            Text(
                text = it.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .wrapContentWidth(Alignment.Start)
                    .animateItemPlacement()
            )
        }
    }
}

/**
 * This logic should live outside UI, but full arch omitted for simplicity in this sample.
 */
private fun getTopics(
    searchTerm: String,
    topics: List<Topic>
): List<Topic> {
    return if (searchTerm != "") {
        topics.filter { it.name.contains(searchTerm, ignoreCase = true) }
    } else {
        topics
    }
}

@Composable
private fun SearchTopBar(searchTerm: TextFieldValue, updateSearchTerm: (TextFieldValue) -> Unit) {
    TopAppBar(elevation = 0.dp) {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = stringResource(
                id = R.string.search
            ),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
        )
        BasicTextField(
            value = searchTerm,
            onValueChange = updateSearchTerm,
            maxLines = 1,
            textStyle = MaterialTheme.typography.subtitle1.copy(color = LocalContentColor.current),
            cursorBrush = SolidColor(LocalContentColor.current),
            modifier = Modifier
                .weight(1F)
                .align(Alignment.CenterVertically)
        )
        IconButton(onClick = {}, modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = stringResource(id = R.string.label_profile)
            )
        }
    }
}

@Preview(name = "SearchTopBar")
@Composable
private fun SearchTopBarPreview() {
    YellowTheme {
        SearchTopBar(searchTerm = TextFieldValue("")) {
        }
    }
}

@Preview(name = "SearchCourses")
@Composable
private fun SearchCoursesPreview() {
    YellowTheme {
        SearchCourses(topics = topics)
    }
}