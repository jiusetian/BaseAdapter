package com.library.baseadapter.rvkt.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
object WrapperUtil {

    fun onAttachedToRecyclerView(innerAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>, recyclerView: RecyclerView,
                                 callback: (GridLayoutManager, GridLayoutManager.SpanSizeLookup, Int) -> Int) {
        innerAdapter.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView.layoutManager

        if (layoutManager is GridLayoutManager) {
            //spansize是网格布局跨度大小，正常一个网格一个跨度
            val spansizeLookup = layoutManager.spanSizeLookup
            //根据不同position的网格设置不同的跨度，callback是自定义实现的一个回调方法，用来实现返回不同的跨度
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return callback.invoke(layoutManager, spansizeLookup, position)
                }
            }

            layoutManager.spanCount = layoutManager.spanCount
        }
    }

    fun setFullSpan(holder: RecyclerView.ViewHolder) {
        val lp = holder.itemView.layoutParams

        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            lp.isFullSpan = true
        }
    }
}