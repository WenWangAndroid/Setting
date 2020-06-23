package cn.xhu.www.setting.utils.rx

import android.content.Context
import androidx.lifecycle.MutableLiveData
import cn.xhu.www.setting.widget.getProgressDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.observeOnMainThread(): Single<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(scheduler: Scheduler = Schedulers.io()): Single<T> =
    subscribeOn(scheduler).observeOnMainThread()

fun <T> Single<T>.status(data: MutableLiveData<Boolean>): Single<T> =
    doOnSubscribe { data.value = true }.doFinally { data.value = false }

fun <T> Single<T>.bindProgressBar(context: Context): Single<T> {
    val progressDialog = context.getProgressDialog()
    return doOnSubscribe {
        progressDialog.show()
    }.doFinally {
        progressDialog.dismiss()
    }
}