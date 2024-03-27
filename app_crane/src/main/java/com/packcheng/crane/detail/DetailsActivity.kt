package com.packcheng.crane.detail

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.annotation.VisibleForTesting
import com.packcheng.crane.data.ExploreModel
import dagger.hilt.android.AndroidEntryPoint

internal const val KEY_ARG_DETAILS_CITY_NAME = "KEY_ARG_DETAILS_CITY_NAME"

fun launchDetailsActivity(context: Context, item: ExploreModel) {
    context.startActivity(createDetailsActivityIntent(context, item))
}

@VisibleForTesting
fun createDetailsActivityIntent(context: Context, item: ExploreModel): Intent {
    val intent = Intent(context, DetailsActivity::class.java)
    intent.putExtra(KEY_ARG_DETAILS_CITY_NAME, item.city.name)
    return intent
}

@AndroidEntryPoint
class DetailsActivity:ComponentActivity() {
}