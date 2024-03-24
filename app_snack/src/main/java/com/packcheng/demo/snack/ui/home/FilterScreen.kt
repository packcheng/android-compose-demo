package com.packcheng.demo.snack.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.packcheng.demo.snack.R
import com.packcheng.demo.snack.model.Filter
import com.packcheng.demo.snack.model.SnackRepo
import com.packcheng.demo.snack.ui.components.ZbcSnackScaffold
import com.packcheng.demo.snack.ui.theme.ZbcSnackTheme

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/30 17:38
 */
@Composable
fun FilterScreen(
    onDismiss: () -> Unit
) {
    var sortState by remember { mutableStateOf(SnackRepo.getSortDefault()) }
    var maxCalories by remember { mutableStateOf(0f) }
    val defaultFilter = SnackRepo.getSortDefault()

    Dialog(onDismissRequest = onDismiss) {
        val priceFilters = remember { SnackRepo.getPriceFilters() }
        val categoryFilters = remember { SnackRepo.getCategoryFilters() }
        val lifeStyleFilters = remember { SnackRepo.getLifeStyleFilters() }

        ZbcSnackScaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.close)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.label_filters),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                },
                actions = {
                    val resetEnabled = sortState != defaultFilter
                    IconButton(
                        onClick = { /* TODO: Open search */ },
                        enabled = resetEnabled
                    ) {
                        val alpha = if (resetEnabled) {
                            ContentAlpha.high
                        } else {
                            ContentAlpha.disabled
                        }
                        CompositionLocalProvider(LocalContentAlpha provides alpha) {
                            Text(
                                text = stringResource(id = R.string.reset),
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                },
                backgroundColor = ZbcSnackTheme.colors.uiBackground
            )
        }) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                SortFiltersSection(sortState = sortState, onFilterChange = { filter ->
                    sortState = filter.name
                })
                FilterChipSection(
                    title = stringResource(id = R.string.price),
                    filters = priceFilters
                )
                FilterChipSection(
                    title = stringResource(id = R.string.category),
                    filters = categoryFilters
                )
                MaxCalories(
                    sliderPosition = maxCalories,
                    onValueChanged = { maxCalories = it })
                FilterChipSection(
                    title = stringResource(id = R.string.lifestyle),
                    filters = lifeStyleFilters
                )
            }
        }
    }
}

@Composable
fun MaxCalories(sliderPosition: Float, onValueChanged: (Float) -> Unit) {
    FlowRow {
        FilterTitle(text = stringResource(id = R.string.max_calories))
        Text(
            text = stringResource(id = R.string.per_serving),
            style = MaterialTheme.typography.body2,
            color = ZbcSnackTheme.colors.brand,
            modifier = Modifier.padding(top = 5.dp, start = 10.dp)
        )
    }

    Slider(
        value = sliderPosition,
        onValueChange = onValueChanged,
        valueRange = 0f..300f,
        steps = 5,
        modifier = Modifier
            .fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = ZbcSnackTheme.colors.brand,
            activeTrackColor = ZbcSnackTheme.colors.brand
        )
    )
}


@Composable
fun FilterChipSection(title: String, filters: List<Filter>) {
    FilterTitle(text = title)
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 16.dp)
            .padding(horizontal = 4.dp),
        mainAxisAlignment = FlowMainAxisAlignment.Center
    ) {
        filters.forEach { filter ->
            com.packcheng.demo.snack.ui.components.FilterChip(
                filter = filter,
                modifier = Modifier.padding(end = 4.dp, bottom = 8.dp)
            )
        }
    }
}

@Composable
fun SortFiltersSection(sortState: String, onFilterChange: (Filter) -> Unit) {
    Column(Modifier.padding(bottom = 24.dp)) {
        FilterTitle(text = stringResource(id = R.string.sort))
        SortFilters(sortState = sortState, onChanged = onFilterChange)
    }
}

@Composable
fun SortFilters(
    sortFilters: List<Filter> = SnackRepo.getSortFilters(),
    sortState: String,
    onChanged: (Filter) -> Unit
) {
    sortFilters.forEach { filter ->
        SortOption(
            text = filter.name,
            icon = filter.icon,
            onClickOption = { onChanged(filter) },
            selected = sortState == filter.name
        )
    }
}

@Composable
fun SortOption(
    text: String,
    icon: ImageVector?,
    onClickOption: () -> Unit,
    selected: Boolean
) {
    Row(modifier = Modifier
        .padding(top = 14.dp)
        .selectable(selected) { onClickOption() }) {
        if (null != icon) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                tint = ZbcSnackTheme.colors.brand
            )
        }
    }
}

@Composable
fun FilterTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = ZbcSnackTheme.colors.brand,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}