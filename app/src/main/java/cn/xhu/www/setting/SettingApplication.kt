package cn.xhu.www.setting

import android.app.Application
import cn.xhu.www.setting.common.commonModule
import cn.xhu.www.setting.ui.wallpaper.wallpaperModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SettingApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SettingApplication)
            modules(
                commonModule,
                wallpaperModule
            )
        }
    }
}
