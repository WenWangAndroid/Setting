package cn.xhu.www.setting.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.DATE_ADDED
import androidx.annotation.IntRange
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

private const val PAGE_SIZE = 100

class WallpaperRepository(private val context: Context) {

    fun getImages(
        @IntRange(from = 1) pageSize: Int = PAGE_SIZE,
        @IntRange(from = 0) page: Int
    ): Single<List<Uri>> {
        return queryWallpaperData(pageSize, page, false)
    }

    fun getVideos(
        @IntRange(from = 1) pageSize: Int = PAGE_SIZE,
        @IntRange(from = 0) page: Int
    ): Single<List<Uri>> {
        return queryWallpaperData(pageSize, page, true)
    }

    private fun queryWallpaperData(
        @IntRange(from = 1) pageSize: Int,
        @IntRange(from = 0) page: Int,
        liveWallpaper: Boolean
    ): Single<List<Uri>> {
        val mediaUrl: Uri = if (liveWallpaper) {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        val order = " $DATE_ADDED DESC limit $pageSize offset ${pageSize * page}"

        return context.contentResolver.query(
            mediaUrl, null, null, null, order
        )?.run {
            Observable.create<Uri> {
                try {
                    val idColumn = getColumnIndexOrThrow(_ID)
                    while (moveToNext()) {
                        val id = getLong(idColumn)
                        val contentUri: Uri = ContentUris.withAppendedId(mediaUrl, id)
                        it.onNext(contentUri)
                    }
                } catch (e: Exception) {
                    it.onError(e)
                } finally {
                    close()
                    it.onComplete()
                }
            }.toList()
        } ?: Single.error(NullPointerException())
    }
}
