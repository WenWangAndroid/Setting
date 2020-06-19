package cn.xhu.www.setting.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.xhu.www.setting.R
import cn.xhu.www.setting.utils.logInfo

class RecyclerViewItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    var drawLeftAndRight = true
    var drawTopAndBottom = true

    //    private val attr = intArrayOf(android.R.attr.listDivider)
    var dividerDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.divider_8dp)

    init {
//        context.obtainStyledAttributes(attr).run {
//            dividerDrawable = getDrawable(0)
//            recycle()
//        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val divider = dividerDrawable ?: return
        val position = parent.layoutManager?.getPosition(view) ?: return
        val spanCount = getSpanCount(parent)
        val childCount = state.itemCount

        var left = 0
        var right = 0
        var top = 0
        var bottom = 0

        when (parent.layoutManager) {
            is GridLayoutManager,
            is StaggeredGridLayoutManager -> {
                val dividerHeight = 16
                val dividerWidth = 16
                val isLastColumn = isLastColumn(parent, position, spanCount, childCount)
                val isLastRow = isLastRow(parent, position, spanCount, childCount)

                if (drawLeftAndRight) {
                    left = dividerWidth
                    right = if (isLastColumn) dividerWidth else 0
                } else {
                    left = 0
                    right = if (isLastColumn) 0 else dividerWidth
                }
                if (drawTopAndBottom) {
                    top = dividerHeight
                    bottom = if (isLastRow) dividerHeight else 0
                } else {
                    top = 0
                    bottom = if (isLastRow) 0 else dividerHeight
                }
            }
//            is LinearLayoutManager -> {
//                val dividerHeight = divider.intrinsicHeight
//                if (drawTopAndBottom) {
//                    top = if (position == 0) dividerHeight else 0
//                    bottom = dividerHeight
//                } else {
//                    top = if (position == 0) dividerHeight else dividerHeight
//                    bottom = 0
//                }
//            }
        }
        outRect.set(left, top, right, bottom)
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        return when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager -> layoutManager.spanCount
            is StaggeredGridLayoutManager -> layoutManager.spanCount
            else -> 0
        }
    }

    private fun isLastColumn(
        parent: RecyclerView,
        position: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager ?: return false
        return when (layoutManager) {
            is GridLayoutManager -> isLastColumnInGridLayout(position, spanCount)
            is StaggeredGridLayoutManager ->
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    isLastColumnInGridLayout(position, spanCount)
                } else {
                    isLastRowInGridLayout(position, spanCount, childCount)
                }
            else -> false
        }
    }

    private fun isLastRow(
        parent: RecyclerView,
        position: Int,
        spanCount: Int,
        childCount: Int
    ): Boolean {
        val layoutManager = parent.layoutManager ?: return false
        return when (layoutManager) {
            is GridLayoutManager -> isLastRowInGridLayout(position, spanCount, childCount)
            is StaggeredGridLayoutManager -> {
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    isLastRowInGridLayout(position, spanCount, childCount)
                } else {
                    isLastColumnInGridLayout(position, spanCount)
                }
            }
            else -> false
        }
    }

    private fun isLastColumnInGridLayout(position: Int, spanCount: Int): Boolean =
        (position + 1) % spanCount == 0

    private fun isLastRowInGridLayout(position: Int, spanCount: Int, childCount: Int): Boolean {
        val remainder = childCount % spanCount
        val lastRowFirstIndex = if (remainder == 0) {
            childCount - spanCount
        } else {
            childCount - remainder
        }
        return position >= lastRowFirstIndex
    }

    companion object {
        const val DRAW_FIRST_LINE = 0

    }
}
