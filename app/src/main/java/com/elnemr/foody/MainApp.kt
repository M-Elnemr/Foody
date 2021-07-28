package com.elnemr.foody

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MainApp : Application() {
    private var instance: MainApp? = null

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }

        instan = instance!!
    }

    companion object{
lateinit var instan : MainApp
    }

}