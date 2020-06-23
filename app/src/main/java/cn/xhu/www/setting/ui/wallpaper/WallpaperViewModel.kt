package cn.xhu.www.setting.ui.wallpaper

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import cn.xhu.www.setting.base.BaseViewModel
import cn.xhu.www.setting.data.WallpaperRepository
import cn.xhu.www.setting.utils.logInfo
import cn.xhu.www.setting.utils.rx.async
import cn.xhu.www.setting.utils.rx.status
import io.reactivex.rxjava3.kotlin.subscribeBy

class WallpaperViewModel(private val wallpaperRepository: WallpaperRepository) : BaseViewModel() {
    private var imagePage = 1
    private var videoPage = 1

    val items: MutableLiveData<List<Uri>> by lazy { MutableLiveData<List<Uri>>() }
    val refreshing = MutableLiveData<Boolean>(false)

    fun getImages() {
        wallpaperRepository.getImages(page = 0)
            .async()
            .status(refreshing)
            .doOnSuccess { imagePage = 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> items.value = list }
            )
            .bind()
    }

    fun getVideos() {
        wallpaperRepository.getVideos(page = 0)
            .async()
            .status(refreshing)
            .doOnSuccess { videoPage = 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> items.value = list }
            )
            .bind()
    }

    fun loadMoreImages() {
        wallpaperRepository.getImages(page = imagePage + 1)
            .async()
            .doOnSuccess { imagePage += 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
            .bind()
    }

    fun loadMoreVideos() {
        wallpaperRepository.getVideos(page = videoPage + 1)
            .async()
            .doOnSuccess { videoPage += 1 }
            .subscribeBy(
                onError = { e -> e.printStackTrace() },
                onSuccess = { list -> logInfo("-------onSuccess------${list.size}") }
            )
            .bind()
    }
}
