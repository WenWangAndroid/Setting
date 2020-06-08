package cn.xhu.www.setting.data

import io.reactivex.Flowable
import java.io.File

class WallpaperRepository : WallpaperDataSource {
    override fun getImages(): Flowable<File> {
        TODO("Not yet implemented")
    }

    override fun getVideos(): Flowable<File> {
        TODO("Not yet implemented")
    }

}