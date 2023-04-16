package com.packcheng.demo.survey.survey

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore

/**
 * Manages the creation of photo Uris. The Uri is used to store the photos taken with camera.
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/4/15 14:32
 */
class PhotoUriManager(private val appContext: Context) {

    private val photoCollection by lazy {
        if (Build.VERSION.SDK_INT > 28) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
    }

    private val resolver by lazy { appContext.contentResolver }

    fun buildNewUri() = resolver.insert(photoCollection, buildPhotoDetails())

    private fun buildPhotoDetails() = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, generateFilename())
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    /**
     * Create a unique file name based on the time the photo is taken
     */
    private fun generateFilename() = "AppSurvey-${System.currentTimeMillis()}.jpg"
}