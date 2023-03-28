package com.ej.tedpermissiontest

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gun0912.tedpermission.PermissionBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestTedPermission(
            {
                Log.d("permission","granted")
            },
            {
                Log.d("permission","denied")
                finish()
            }
        )
    }

    private fun requestTedPermission(grantedLogic: () -> Unit, deniedLogic: () -> Unit) {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener{
                override fun onPermissionGranted() {
                    grantedLogic()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    deniedLogic()
                }
            })
            .setPermissionsWithOs()
            .check()
    }
}
fun TedPermission.Builder.setPermissionsWithOs() = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS)
    else
        setPermissions(Manifest.permission.READ_PHONE_STATE)
}