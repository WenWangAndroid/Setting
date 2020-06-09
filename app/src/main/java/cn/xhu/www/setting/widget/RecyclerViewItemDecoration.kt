package cn.xhu.www.setting.widget


import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import cn.xhu.www.setting.R

class RecyclerViewItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    var drawLeftAndRight = false
    var drawTopAndBottom = false

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

        when (parent.layoutManager) {
            is GridLayoutManager,
            is StaggeredGridLayoutManager -> {
                val dividerHeight = divider.intrinsicHeight
                val dividerWidth = divider.intrinsicWidth

                val isLastColumn = isLastColumn(parent, position, spanCount, childCount)
                val isLastRow = isLastRow(parent, position, spanCount, childCount)

                val left: Int
                val right: Int
                if (drawLeftAndRight) {
                    left = dividerWidth
                    right = if (isLastColumn) dividerWidth else 0
                } else {
                    left = 0
                    right = if (isLastColumn) 0 else dividerWidth
                }

                if (drawTopAndBottom) {
                    if (position == 0) {
                        outRect.set(left, dividerHeight, right, dividerHeight)
                    } else {
                        outRect.set(left, 0, right, dividerHeight)
                    }
                } else {
                    if (position == 0) {
                        outRect.set(0, 0, 0, 0)
                    } else {
                        outRect.set(0, dividerHeight, 0, 0)
                    }
                }
            }
            is LinearLayoutManager -> {
                val dividerHeight = divider.intrinsicHeight
                if (drawTopAndBottom) {
                    if (position == 0) {
                        outRect.set(0, dividerHeight, 0, dividerHeight)
                    } else {
                        outRect.set(0, 0, 0, dividerHeight)
                    }
                } else {
                    if (position == 0) {
                        outRect.set(0, 0, 0, 0)
                    } else {
                        outRect.set(0, dividerHeight, 0, 0)
                    }
                }
            }
            else -> super.getItemOffsets(outRect, view, parent, state)
        }
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
        return when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager ->
                (position + 1) % spanCount == 0
            is StaggeredGridLayoutManager ->
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    (position + 1) % spanCount == 0
                } else {
                    position >= childCount - childCount % spanCount
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
        return when (val layoutManager = parent.layoutManager) {
            is GridLayoutManager ->
                position >= childCount - childCount % spanCount
            is StaggeredGridLayoutManager -> {
                if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
                    position >= childCount - childCount % spanCount
                } else {
                    (position + 1) % spanCount == 0
                }
            }
            else -> false
        }
    }

    companion object {
        const val DRAW_FIRST_LINE = 0

    }
}
