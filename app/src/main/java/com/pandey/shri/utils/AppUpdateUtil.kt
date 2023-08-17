package com.pandey.shri.utils

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class AppUpdateUtil {

    fun checkForUpdates(activity: Activity) {
        // Create an instance of the app update manager
        val appUpdateManager = AppUpdateManagerFactory.create(activity)

        // Check if an update is available
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Start the update if available
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    activity,
                    Constant.REQUEST_CODE_UPDATE
                )
            }
        }
    }
}