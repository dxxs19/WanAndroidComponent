package com.x.lib_base.utils.ui

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 *  屏幕适配
 */
class FitScreen {

    companion object {
        private val defaultWidth = 360f
        private var appDensity = 0f
        private var appScaleDensity = 0f

        fun setCustomDensity(application: Application, activity: Activity) {
            val displayMetrics = application.resources.displayMetrics

            if (appDensity == 0f) {
                appDensity = displayMetrics.density
                appScaleDensity = displayMetrics.scaledDensity
                application.registerComponentCallbacks(object : ComponentCallbacks {
                    override fun onConfigurationChanged(newConfig: Configuration) {
                        if (newConfig.fontScale > 0) {
                            appScaleDensity = application.resources.displayMetrics.scaledDensity
                        }
                    }

                    override fun onLowMemory() {

                    }
                })
            }

            val targetDensity = displayMetrics.widthPixels/ defaultWidth
            val targetScaleDensity = targetDensity * (appScaleDensity / appDensity)
            val targetDensityDpi = targetDensity * 160

            displayMetrics.density = targetDensity
            displayMetrics.scaledDensity = targetScaleDensity
            displayMetrics.densityDpi = targetDensityDpi.toInt()

            val activityDisplayMetrics = activity.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity
            activityDisplayMetrics.scaledDensity = targetScaleDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi.toInt()
        }
    }
}