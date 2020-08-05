package com.library.baseadapter.rvkt.wrapper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
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
    private var enableLoadmore = true // 是否可加载更多
    private lateinit var loadMoreListener: () -> Unit

    /**
     * 加载更多的监听接口
     */
    fun setOnLoadMoreListener(loadMoreListener: () -> Unit): LoadMoreWrapper {
        this.loadMoreListener = loadMoreListener
        return this
    }

    fun setLoadmoreViewVisiable(visible: Int) {
        loadMoreView?.visibility = visible
    }

    fun setEnableLoadmore(enable: Boolean) {
        enableLoadmore = enable
    }

    fun setLoadMoreView(loadMoreView: View): LoadMoreWrapper {
        this.loadMoreView = loadMoreView
        setLoadmoreViewVisiable(View.INVISIBLE)
        return this
    }

    fun setLoadMoreViewId(loadMoreViewId: Int, context: Context): LoadMoreWrapper {
        this.loadMoreView = LayoutInflater.from(context).inflate(loadMoreViewId, null);
        setLoadmoreViewVisiable(View.INVISIBLE)
        return this
    }

    fun hasLoadmore(): Boolean = loadMoreView != null

    // 是否执行加载更多
    fun isShowLoadmore(position: Int): Boolean {
        // 主要判断当前pos是否大于等于数据的大小
        return hasLoadmore() && enableLoadmore && position >= innerAdapter.itemCount
    }


    override fun getItemViewType(position: Int): Int {
        return if (isShowLoadmore(position)) ITEM_TYPE_LOAD_MORE else innerAdapter.getItemViewType(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 加载更多的布局
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            var viewHolder: ViewHolder? = null
            if (loadMoreView != null) {
                viewHolder = ViewHolder.createViewHolder(parent.context, loadMoreView!!)
            }
            return viewHolder!!
        }
        // 其他
        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount + if (hasLoadmore()) 1 else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 过滤当还没有数据的时候调用了加载更多
        if (innerAdapter.itemCount == 0) return
        // 回调加载更多
        if (isShowLoadmore(position)) {
            setLoadmoreViewVisiable(View.VISIBLE)
            loadMoreListener?.invoke()
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