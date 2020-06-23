package cn.xhu.www.setting.ui.wallpaper.service

import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import cn.xhu.www.setting.ui.wallpaper.getLiveWallpaperFile
import java.lang.Exception

class LiveWallpaperService : WallpaperService() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateEngine(): Engine = LiveWallpaperEngine()

    inner class LiveWallpaperEngine : Engine() {
        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val dataPath = getLiveWallpaperFile()?.absolutePath
            mediaPlayer = MediaPlayer().apply {
                try {
                    setSurface(holder.surface)
                    setDataSource(dataPath)
                    isLooping = true
                    setVolume(0f, 0f)
//                    setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    prepare()
                    start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            mediaPlayer?.run {
                if (visible) start() else pause()
            }
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}