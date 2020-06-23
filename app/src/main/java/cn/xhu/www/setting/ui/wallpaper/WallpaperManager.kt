package cn.xhu.www.setting.ui.wallpaper

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import cn.xhu.www.setting.ui.wallpaper.service.LiveWallpaperService
import io.reactivex.rxjava3.core.Single
import java.io.File

private const val LiveWallpaperBackupName = "LiveWallpaperBackup.mp4"

fun Context.getLiveWallpaperFile(): File? {
    val parentFile = externalCacheDir ?: return null
    return File(parentFile, LiveWallpaperBackupName)
}

fun Context.saveLiveWallpaper(uri: Uri): Single<Boolean> {
    val outPutStream = getLiveWallpaperFile()?.outputStream()
    val inputStream = contentResolver.openInputStream(uri)
    if (outPutStream == null || inputStream == null) {
        return Single.just(false)
    }
    return Single.create<Boolean> {
        try {
            inputStream.copyTo(outPutStream)
            it.onSuccess(true)
        } catch (e: Exception) {
            it.onError(e)
        } finally {
            try {
                inputStream.close()
                outPutStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun Context.liveWallpaperIsRunning(): Boolean {
    return try {
        packageName == WallpaperManager.getInstance(this).wallpaperInfo.packageName
    } catch (e: Exception) {
        false
    }
}

fun Context.getLiveWallpaperIntent(): Intent {
    return Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
        putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(this@getLiveWallpaperIntent, LiveWallpaperService::class.java)
        )
    }
}

fun Context.clearOwnWallpaper() {
    try {
        if (liveWallpaperIsRunning())
            WallpaperManager.getInstance(this).clear()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}