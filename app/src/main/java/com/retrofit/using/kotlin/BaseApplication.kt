package com.retrofit.using.kotlin

import android.app.Application
import android.content.res.Configuration
import android.util.Log

class BaseApplication : Application() {

    companion object {
        private val TAG: String = BaseApplication::class.java.simpleName

        private var INSTANCE: BaseApplication? = null

        fun getInstance(): BaseApplication {
            return INSTANCE as BaseApplication
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "LANDSCAPE")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "PORTRAIT")
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}