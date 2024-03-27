package com.packcheng.crane.util

import android.util.Log

object ZbcLog {
    var enable = true

    fun d(tag: String, msg: String) {
        if (enable) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (enable) {
            Log.i(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (enable) {
            Log.e(tag, msg)
        }
    }
}