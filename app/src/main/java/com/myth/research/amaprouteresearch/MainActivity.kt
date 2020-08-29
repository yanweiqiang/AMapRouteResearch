package com.myth.research.amaprouteresearch

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionMgr(this).run {
            if (!hasPermissions(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        LocationRecorderService.startServiceForeground(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationRecorderService.stopService(this)
    }
}