package cn.xhu.www.setting.data

import android.content.Intent
import android.graphics.drawable.Drawable

data class AppInfo(
    val name: String,
    val icon: Drawable,
    val intent: Intent,
    val packageName: String
)