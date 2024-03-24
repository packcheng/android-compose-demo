package com.packcheng.demo.snack.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.packcheng.demo.snack.model.Filter
import com.packcheng.demo.snack.model.SnackCollection
import com.packcheng.demo.snack.model.SnackRepo
import com.packcheng.demo.snack.ui.components.FilterBar
import com.packcheng.demo.snack.ui.components.SnackCollection
import com.packcheng.demo.snack.ui.components.ZbcSnackSurface

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/28 17:52
 */

@Composable
fun Feed(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackCollections = remember { SnackRepo.getSnacks() }
    val filters = remember { SnackRepo.getFilters() }
    Feed(
        snackCollections,
        filters,
        onSnackClick,
        modifier
    )
}

@Composable
private fun Feed(
    snackCollections: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ZbcSnackSurface(modifier = modifier.fillMaxSize()) {
        Box {
            SnackCollectionList(
                snackCollections = snackCollections,
                filters = filters,
                onSnackClick = onSnackClick
            )
            DestinationBar()
        }
    }
}

@Composable
private fun SnackCollectionList(
    snackCollections: List<SnackCollection>,
    filters: List<Filter>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Box(modifier = modifier) {
        LazyColumn {
            item {
                Spacer(
                    Modifier.windowInsetsTopHeight(
                        WindowInsets.statusBars.add(WindowInsets(top = 56.dp))
                    )
                )
                FilterBar(filters = filters, onShowFilters = { filtersVisible = true })
            }
            itemsIndexed(snackCollections) { index, collection ->
                SnackCollection(
                    snackCollection = collection,
                    onSnackClick = onSnackClick,
                    index = index
                )
            }
        }
    }

    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false }
        )
    }
}
