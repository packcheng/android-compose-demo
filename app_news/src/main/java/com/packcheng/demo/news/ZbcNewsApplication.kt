package com.packcheng.demo.news

import android.app.Application
import com.packcheng.demo.news.data.AppContainer
import com.packcheng.demo.news.data.AppContainerImpl

/**
 *
 *
 * @author packcheng <a href="mailto:packcheng_jo@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2023/9/1 16:03
 */
class ZbcNewsApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}