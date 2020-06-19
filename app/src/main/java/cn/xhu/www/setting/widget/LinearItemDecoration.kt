package cn.xhu.www.setting.widget

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class LinearItemDecoration : RecyclerView.ItemDecoration() {
    var drawFirstLine = true

    var drawLastLine = true

    @DrawableRes
    var lineDrawable: Int? = null

    @Px
    var lineHeight: Int = 0

    @ColorRes
    var lineColor: Int? = null


}