package cn.xhu.www.setting.common

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val appPreferences: SharedPreferences =
        context.getSharedPreferences(APP_SP_NAME, MODE_PRIVATE)

    var isFirstLaunch: Boolean
        get() = appPreferences.getBoolean(FIRST_LAUNCH, true)
        set(value) {
            appPreferences.edit().putBoolean(FIRST_LAUNCH, false).apply()
        }

    companion object {
        private const val APP_SP_NAME = "app"
        private const val FIRST_LAUNCH = "first_launch"
    }
}
