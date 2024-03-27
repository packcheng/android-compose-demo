package com.packcheng.crane.util

import coil.intercept.Interceptor
import coil.request.ImageResult
import coil.size.pxOrElse
import okhttp3.HttpUrl.Companion.toHttpUrl

object UnsplashSizingInterceptor : Interceptor {
    private val TAG = "UnsplashSizingInterceptor"

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val data = chain.request.data
        val widthPx = chain.size.width.pxOrElse { -1 }
        val heightPx = chain.size.height.pxOrElse { -1 }
        if (widthPx > 0 && heightPx > 0 && data is String && data.startsWith("https://images.unsplash.com/photo-")) {
            ZbcLog.d(TAG, "original url:$data")
            val url = data.toHttpUrl()
                .newBuilder()
                .addQueryParameter("w", widthPx.toString())
                .addQueryParameter("h", heightPx.toString())
                .build()
            ZbcLog.d(TAG, "new url: $url")
            val request = chain.request.newBuilder().data(url).build()
            return chain.proceed(request)
        }
        return chain.proceed(chain.request)
    }
}