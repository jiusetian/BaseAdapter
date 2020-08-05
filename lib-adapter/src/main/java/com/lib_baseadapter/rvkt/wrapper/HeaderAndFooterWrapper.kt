package com.library.baseadapter.rvkt.wrapper

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.library.baseadapter.rvkt.base.ViewHolder
import com.library.baseadapter.rvkt.utils.WrapperUtil

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：装饰者模式，用一个adapter去装饰另一个adapter，装饰者可以根据不同的情况去调用被装饰者的方法，比如在这个类中，装饰者负责跟外界接触
 * recycleview不管adapter里面是怎么实现的，adapter只需要重写了rv需要的几个方法，比如返回itemcount、itemview类型，返回指定viewholder，所以
 * 我们可以根据需求在里面插入头布局或脚布局，还可以在指定位置插入加载更多的布局
 *
 */
class HeaderAndFooterWrapper(val innerAdapter: RecyclerView.Adapter<ViewHolder>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val BASE_ITEM_TYPE_HEADER = 100000
        const val BASE_ITEM_TYPE_FOOTER = 200000
    }

    private val headerViews by lazy { SparseArrayCompat<View>() }

    private val footerViews by lazy { SparseArrayCompat<View>() }

    override fun getItemViewType(position: Int): Int {

        if (isHeaderViewPos(position)) {
            return headerViews.keyAt(position)
        } else if (isFooterViewPos(position)) {
            return footerViews.keyAt(position - getHeadersCount() - getRealItemCount())
        }
        return innerAdapter.getItemViewType(position-getHeadersCount())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        headerViews[viewType]?.let { return ViewHolder.createViewHolder(parent.context, it) }
        footerViews[viewType]?.let { return ViewHolder.createViewHolder(parent.context, it) }

        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return getRealItemCount() + getHeadersCount() + getFootersCount()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (isHeaderViewPos(position)) return
        if (isFooterViewPos(position)) return

        innerAdapter.onBindViewHolder(holder, position - getHeadersCount())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        WrapperUtil.onAttachedToRecyclerView(innerAdapter, recyclerView) a@{ layoutManager, oldLookup, position ->
            //根据不同的网格类型viewType，设置不同的跨度大小
            val viewType = getItemViewType(position)
            headerViews[viewType]?.let { return@a layoutManager.spanCount }
            footerViews[viewType]?.let { return@a layoutManager.spanCount }
            oldLookup?.let { return@a it.getSpanSize(position) }
            return@a 1
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        innerAdapter.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        if (isHeaderViewPos(position) || isFooterViewPos(position))
            WrapperUtil.setFullSpan(holder)
    }

    fun isHeaderViewPos(position: Int): Boolean = position < getHeadersCount()

    fun getRealItemCount() = innerAdapter.itemCount

    fun isFooterViewPos(position: Int): Boolean = position >= getHeadersCount() + getRealItemCount()

    fun addHeaderView(view: View) {
        headerViews.put(headerViews.size() + BASE_ITEM_TYPE_HEADER, view)
    }

    fun addFooterView(view: View) {
        footerViews.put(footerViews.size() + BASE_ITEM_TYPE_FOOTER, view)
    }

    fun getHeadersCount() = headerViews.size()

    fun getFootersCount() = footerViews.size()

}