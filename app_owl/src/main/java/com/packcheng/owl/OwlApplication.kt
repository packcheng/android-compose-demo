package com.packcheng.owl

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.compose.AsyncImage
import coil.util.DebugLogger
import com.packcheng.owl.ui.utils.UnsplashSizingInterceptor

class OwlApplication : Application(), ImageLoaderFactory {

    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            .logger(DebugLogger())
            .respectCacheHeaders(false)
            .build()
    }

}