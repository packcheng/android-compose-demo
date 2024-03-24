package com.packcheng.demo.snack.ui.home.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.packcheng.demo.snack.model.SearchCategory
import com.packcheng.demo.snack.model.SearchCategoryCollection
import com.packcheng.demo.snack.ui.components.SnackImage
import com.packcheng.demo.snack.ui.components.VerticalGrid
import com.packcheng.demo.snack.ui.theme.ZbcSnackTheme
import kotlin.math.max

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/11/29 23:41
 */

@Composable
fun SearchCategories(
    categories: List<SearchCategoryCollection>,
    onCategoryClicked: (String) -> Unit = {}
) {
    LazyColumn {
        itemsIndexed(categories) { index, categoriesGroup ->
            SearchCategoryCollection(
                collection = categoriesGroup,
                index = index,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun SearchCategoryCollection(
    modifier: Modifier = Modifier,
    collection: SearchCategoryCollection,
    index: Int,
    onCategoryClicked: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(
            text = collection.name,
            style = MaterialTheme.typography.h6,
            color = ZbcSnackTheme.colors.textPrimary,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(horizontal = 24.dp, vertical = 4.dp)
                .wrapContentHeight()
        )
        VerticalGrid(modifier = Modifier.padding(horizontal = 16.dp)) {
            val gradient = when (index % 2) {
                0 -> ZbcSnackTheme.colors.gradient2_2
                else -> ZbcSnackTheme.colors.gradient2_3
            }
            collection.categories.forEach { category ->
                SearchCategory(
                    category = category,
                    gradient = gradient,
                    modifier = Modifier.padding(8.dp),
                    onCategoryClicked = onCategoryClicked
                )
            }
        }
        Spacer(Modifier.height(4.dp))
    }
}

private val MinImageSize = 134.dp
private val CategoryShape = RoundedCornerShape(10.dp)
private const val CategoryTextProportion = 0.55f

@Composable
private fun SearchCategory(
    modifier: Modifier = Modifier,
    category: SearchCategory,
    gradient: List<Color>,
    onCategoryClicked: (String) -> Unit = {},
) {
    Layout(modifier = modifier
        .aspectRatio(1.45F)
        .shadow(elevation = 3.dp, shape = CategoryShape)
        .clip(CategoryShape)
        .background(Brush.horizontalGradient(gradient))
        .clickable { onCategoryClicked(category.name) },
        content = {
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
                color = ZbcSnackTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(4.dp)
                    .padding(start = 8.dp)
            )
            SnackImage(
                imageUrl = category.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }) { measurables, constraints ->
        // Text given a set proportion of width (which is determined by the aspect ratio)
        val textWidth = (constraints.maxWidth * CategoryTextProportion).toInt()
        val textPlaceable = measurables[0].measure(Constraints.fixedWidth(textWidth))

        // Image is sized to the larger of height of item, or a minimum value
        // i.e. may appear larger than item (but clipped to the item bounds)
        val imageSize = max(MinImageSize.roundToPx(), constraints.maxHeight)
        val imagePlaceable =
            measurables[1].measure(Constraints.fixed(width = imageSize, height = imageSize))

        layout(width = constraints.maxWidth, height = constraints.minHeight) {
            textPlaceable.placeRelative(
                x = 0,
                y = (constraints.maxHeight - textPlaceable.height) / 2
            )
            imagePlaceable.placeRelative(
                x = textWidth,
                y = (constraints.maxHeight - imagePlaceable.height) / 2
            )
        }
    }
}