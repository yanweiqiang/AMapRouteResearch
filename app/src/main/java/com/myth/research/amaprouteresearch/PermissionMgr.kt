package com.myth.research.amaprouteresearch

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionMgr(private val activity: Activity) {
    fun hasPermissions(vararg permissions: String): Boolean {
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }

        return true
    }

    fun requestPermissions(vararg permissions: String) {
        ActivityCompat.requestPermissions(activity, permissions, 0xf1)
    }
}