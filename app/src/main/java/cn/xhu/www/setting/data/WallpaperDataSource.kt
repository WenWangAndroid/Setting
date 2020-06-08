package cn.xhu.www.setting.data

import io.reactivex.Flowable
import java.io.File

interface WallpaperDataSource {

    fun getImages(): Flowable<File>

    fun getVideos(): Flowable<File>
}
