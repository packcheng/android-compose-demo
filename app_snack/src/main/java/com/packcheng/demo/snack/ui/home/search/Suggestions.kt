package com.packcheng.demo.snack.ui.home.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packcheng.demo.snack.model.SearchSuggestionGroup
import com.packcheng.demo.snack.ui.theme.ZbcSnackTheme

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/29 23:41
 */

@Composable
fun SearchSuggestions(
    suggestions: List<SearchSuggestionGroup>,
    onSuggestionSelect: (String) -> Unit
) {
    LazyColumn {
        suggestions.forEach { suggestionsGroup ->
            item { SuggestionHeader(name = suggestionsGroup.name) }
            items(suggestionsGroup.suggestions) { suggestion ->
                Suggestion(suggestion = suggestion, onSuggestionSelect = onSuggestionSelect)
            }
            item { Spacer(Modifier.height(4.dp)) }
        }
    }
}


@Composable
private fun Suggestion(
    suggestion: String,
    onSuggestionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(text = suggestion,
        style = MaterialTheme.typography.subtitle1,
        modifier = modifier
            .heightIn(min = 48.dp)
            .fillMaxWidth()
            .clickable { onSuggestionSelect(suggestion) }
            .padding(start = 24.dp)
            .wrapContentSize(Alignment.CenterStart))
}

@Composable
private fun SuggestionHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = MaterialTheme.typography.h6,
        color = ZbcSnackTheme.colors.textPrimary,
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}