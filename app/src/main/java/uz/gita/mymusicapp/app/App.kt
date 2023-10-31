package uz.gita.mymusicapp.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp

/**
 *    Created by Kamolov Samandar on 31.10.2023 at 12:54
 */

@HiltAndroidApp
class App : Application() {
    companion object{
        lateinit var instance: App
            private set

        var isNightMode : Boolean = true
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        isNightMode = isNightMode(this)
    }

    private fun isNightMode(context: Context): Boolean{
        val configuration = context.resources.configuration
        return configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}