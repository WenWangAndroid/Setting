package cn.xhu.www.setting.widget

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import cn.xhu.www.setting.R

fun Context.getProgressDialog(): AlertDialog {
    val progressBar = ProgressBar(this)
    return AlertDialog.Builder(this, R.style.ProgressDialogStyle)
        .setView(progressBar)
        .setCancelable(false)
        .create()
}
