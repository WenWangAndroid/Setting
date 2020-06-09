package cn.xhu.www.setting.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.Size
import androidx.core.content.ContextCompat

fun Context.hasPermissions(@Size(min = 1) vararg permissions: String): Boolean =
    (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
