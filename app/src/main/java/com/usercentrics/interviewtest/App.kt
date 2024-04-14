package com.usercentrics.interviewtest

import android.app.Application
import android.content.Context
import com.usercentrics.interviewtest.utils.Conts.SETTING_KEY
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        val options = UsercentricsOptions(settingsId = SETTING_KEY)
        Usercentrics.initialize(this, options)
    }

    companion object {
        var instance: App? = null
        fun getAppContext(): Context {
            return instance as Context
        }
    }
}
