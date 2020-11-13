package com.example.cryptocoins

import android.util.Log
import androidx.multidex.MultiDexApplication
import androidx.viewbinding.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class Application : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    inner class DebugTree : Timber.DebugTree() {
        // Add the line number to the tag
        override fun createStackElementTag(element: StackTraceElement): String? {
            return super.createStackElementTag(element) + ":" + element.lineNumber
        }
    }

    inner class ReleaseTree : Timber.Tree() {

        override fun isLoggable(tag: String?, priority: Int): Boolean {
            return priority == Log.ERROR || priority == Log.WARN
        }

        override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
            if (isLoggable(tag, priority)) {
                // Log to Firebase
//                FirebaseCrashlytics.getInstance().log(message)
//                if (throwable != null) {
//                    FirebaseCrashlytics.getInstance().recordException(throwable)
//                }
            }
        }
    }
}