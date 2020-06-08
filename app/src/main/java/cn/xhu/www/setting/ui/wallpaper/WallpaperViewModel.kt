package cn.xhu.www.setting.ui.wallpaper

import cn.xhu.www.setting.base.BaseViewModel
import cn.xhu.www.setting.data.WallpaperRepository
import cn.xhu.www.setting.utils.logInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class WallpaperViewModel(private val wallpaperRepository: WallpaperRepository) : BaseViewModel() {
    init {
        wallpaperRepository.getVideos(page = 1)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
    }

    fun test() {

    }
}