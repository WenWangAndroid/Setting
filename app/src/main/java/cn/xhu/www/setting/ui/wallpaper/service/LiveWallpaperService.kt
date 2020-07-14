package cn.xhu.www.setting.ui.wallpaper.service

import android.media.MediaPlayer
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import cn.xhu.www.setting.ui.wallpaper.getLiveWallpaperFile

class LiveWallpaperService : WallpaperService() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer().apply {
            isLooping = true
            setVolume(0f, 0f)
        }
    }

    override fun onCreateEngine(): Engine = LiveWallpaperEngine()

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    internal inner class LiveWallpaperEngine :
        Engine() {
        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            val path = getLiveWallpaperFile()?.absolutePath ?: return
            mediaPlayer.runCatching {
                setSurface(holder.surface)
                setDataSource(path)
                setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                setOnPreparedListener { obj: MediaPlayer -> obj.start() }
                prepare()
            }
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                mediaPlayer.start()
            } else {
                mediaPlayer.pause()
            }
        }
    }
}