package com.library.baseadapter.rvkt.wrapper

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import com.library.baseadapter.rvkt.base.ViewHolder
import com.library.baseadapter.rvkt.utils.WrapperUtil

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
class LoadMoreWrapper(val innerAdapter: RecyclerView.Adapter<ViewHolder>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2
    }

    private var loadMoreView: View? = null
    private var loadMoreViewId = 0
    private lateinit var loadMoreListener: () -> Unit

    /**
     * 加载更多的监听接口
     */
    fun setOnLoadMoreListener(loadMoreListener: () -> Unit): LoadMoreWrapper {
        this.loadMoreListener = loadMoreListener
        return this
    }


    fun setLoadMoreView(loadMoreView: View): LoadMoreWrapper {
        this.loadMoreView = loadMoreView
        return this
    }

    fun setLoadMoreViewId(loadMoreViewId: Int): LoadMoreWrapper {
        this.loadMoreViewId = loadMoreViewId
        return this
    }

    fun hasLoadmore(): Boolean = loadMoreView != null || loadMoreViewId != 0

    fun isShowLoadmore(position: Int): Boolean = hasLoadmore() && position >= innerAdapter.itemCount

    override fun getItemViewType(position: Int): Int {
        return if (isShowLoadmore(position)) ITEM_TYPE_LOAD_MORE else innerAdapter.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            var viewHolder: ViewHolder? = null

            if (loadMoreView != null) {
                viewHolder = ViewHolder.createViewHolder(parent.context, loadMoreView!!)
            } else if (loadMoreViewId != 0) {
                viewHolder = ViewHolder.createViewHolder(parent.context, parent, loadMoreViewId)
            }
            return viewHolder!!
        }
        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount + if (hasLoadmore()) 1 else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //回调加载更多
        if (isShowLoadmore(position)) {
            loadMoreListener?.let {
                it.invoke()
            }
            return
        }
        return innerAdapter.onBindViewHolder(holder, position)
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        WrapperUtil.onAttachedToRecyclerView(innerAdapter, recyclerView) a@{ layoutManager, oldLookup, position ->
            if (isShowLoadmore(position)) {
                return@a layoutManager.spanCount
            }
            oldLookup?.let {
                return@a oldLookup.getSpanSize(position)
            }
            return@a 1
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        innerAdapter.onViewAttachedToWindow(holder)
        if (isShowLoadmore(holder.layoutPosition))
            setFullSpan(holder)
    }

    fun setFullSpan(holder: RecyclerView.ViewHolder) {
        val lp = holder.itemView.layoutParams
        if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
            lp.isFullSpan = true
        }
    }

}