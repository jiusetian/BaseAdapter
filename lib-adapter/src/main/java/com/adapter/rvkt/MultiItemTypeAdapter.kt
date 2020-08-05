package com.adapter.rvkt

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.library.baseadapter.rvkt.base.ItemViewProxy
import com.library.baseadapter.rvkt.base.ItemViewProxyManager
import com.library.baseadapter.rvkt.base.ViewHolder

/**
 * Author：Alex
 * Date：2019/7/18
 * Note：
 */
open class MultiItemTypeAdapter<T>(val context: Context, val datas: MutableList<T>) : RecyclerView.Adapter<ViewHolder>() {

    protected var itemClickListener: OnItemClickListener? = null

    protected val itemViewProxyManager by lazy { ItemViewProxyManager<T>() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewProxy = itemViewProxyManager.getProxy(viewType)
        val itemViewId = itemViewProxy.getItemViewLayouId()
        val viewHolder = ViewHolder.createViewHolder(context, parent, itemViewId)
        onViewHolderCreate(viewHolder, viewHolder.itemView)
        setListener(parent, viewHolder, viewType)
        return viewHolder
    }

    fun onViewHolderCreate(viewHolder: ViewHolder, itemView: View) {

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        if (!useItemViewProxyManager()) return super.getItemViewType(position)
        return itemViewProxyManager.getItemViewType(datas[position], position)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        convert(viewHolder, datas[position])
    }

    /**
     * 转换数据
     */
    fun convert(viewHolder: ViewHolder, t: T) {
        itemViewProxyManager.convert(viewHolder, t, viewHolder.adapterPosition)
    }

    fun getListDatas() = datas

    fun addItemViewProxy(itemViewProxy: ItemViewProxy<T>): MultiItemTypeAdapter<T> {
        itemViewProxyManager.addProxy(itemViewProxy)
        return this
    }

    fun addItemViewProxy(viewType: Int, itemViewProxy: ItemViewProxy<T>): MultiItemTypeAdapter<T> {
        itemViewProxyManager.addProxy(viewType, itemViewProxy)
        return this
    }

    protected fun useItemViewProxyManager() = itemViewProxyManager.getItemViewProxyCount() > 0


    protected fun isEnable(viewType: Int): Boolean {
        return true
    }

    //设置监听
    protected fun setListener(parent: ViewGroup, viewHolder: ViewHolder, viewType: Int) {
        if (!isEnable(viewType)) return
        viewHolder.getConvertView().apply {
            setOnClickListener { v ->
                itemClickListener?.let {
                    val position = viewHolder.adapterPosition
                    it.onItemClick(v, viewHolder, position)
                }
            }

            setOnLongClickListener a@{ v ->
                itemClickListener?.let {
                    val position = viewHolder.adapterPosition
                    return@a it.onItemLongClick(v, viewHolder, position)
                }
                return@a false
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(view: View, viewHolder: RecyclerView.ViewHolder, position: Int)
        fun onItemLongClick(view: View, viewHolder: RecyclerView.ViewHolder, position: Int): Boolean
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

}