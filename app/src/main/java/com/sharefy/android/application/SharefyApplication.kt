package com.sharefy.android.application

import android.app.Application
import com.sharefy.android.utils.AppPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SharefyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}