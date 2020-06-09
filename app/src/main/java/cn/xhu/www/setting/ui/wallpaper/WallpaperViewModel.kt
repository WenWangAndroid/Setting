package cn.xhu.www.setting.ui.wallpaper

import android.net.Uri
import cn.xhu.www.setting.base.BaseViewModel
import cn.xhu.www.setting.data.WallpaperRepository
import cn.xhu.www.setting.utils.logInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class WallpaperViewModel(private val wallpaperRepository: WallpaperRepository) : BaseViewModel() {
    private var imagePage = 1
    private var videoPage = 1

    fun getImages(): Single<List<Uri>> {
        return wallpaperRepository.getImages(page = 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { imagePage = 1 }
//            .subscribeBy(
//                onError = { e -> e.printStackTrace() },
//                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
//            )
    }

    fun getVideos() {
        wallpaperRepository.getVideos(page = 1)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { videoPage = 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
    }

    fun loadMoreImages() {
        wallpaperRepository.getImages(page = imagePage + 1)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { imagePage += 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
    }

    fun loadMoreVideos() {
        wallpaperRepository.getVideos(page = videoPage + 1)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { videoPage += 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
    }
}
