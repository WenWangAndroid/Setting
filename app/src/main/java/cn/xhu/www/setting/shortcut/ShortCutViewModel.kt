package cn.xhu.www.setting.shortcut

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import cn.xhu.www.setting.data.AppInfo

class ShortCutViewModel(application: Application) : AndroidViewModel(application) {

    private fun getAppList(context: Context): List<AppInfo>? {
        val list: MutableList<AppInfo> = ArrayList()
        val packageManager: PackageManager = context.packageManager
        val mainIntent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val resolveInfos = packageManager.queryIntentActivities(mainIntent, 0)
        for (info in resolveInfos) {
            val packName = info.activityInfo.packageName
            if (packName == context.packageName) {
                continue
            }

            val appIcon = info.activityInfo.applicationInfo.loadIcon(packageManager)
            val appName = info.activityInfo.applicationInfo.loadLabel(packageManager).toString()
            val intent =
                Intent().apply { component = ComponentName(packName, info.activityInfo.name) }
            list.add(AppInfo(appName, appIcon, intent, packName))
        }
        return list
    }
}