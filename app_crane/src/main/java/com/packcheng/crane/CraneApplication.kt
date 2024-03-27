package com.packcheng.crane

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.packcheng.crane.util.UnsplashSizingInterceptor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CraneApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .components { add(UnsplashSizingInterceptor) }
            .build()
    }

}