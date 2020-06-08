package cn.xhu.www.setting.utils

import android.util.Log
import cn.xhu.www.setting.BuildConfig

fun Any.logInfo(message: String) {
    if (BuildConfig.DEBUG) {
        Log.i(this.javaClass.name, message)
    }
}