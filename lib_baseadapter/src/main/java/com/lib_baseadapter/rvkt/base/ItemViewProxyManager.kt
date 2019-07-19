package com.library.baseadapter.rvkt.base

import android.support.v4.util.SparseArrayCompat

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
class ItemViewProxyManager<T> {

    private val proxys by lazy { SparseArrayCompat<ItemViewProxy<T>>() }

    fun getItemViewProxyCount(): Int = proxys.size()


    fun addProxy(itemViewProxy: ItemViewProxy<T>): ItemViewProxyManager<T> {
        var viewType = proxys.size()
        proxys.put(viewType, itemViewProxy)
        return this
    }

    fun addProxy(viewType: Int, itemViewProxy: ItemViewProxy<T>): ItemViewProxyManager<T> {
        if (proxys[viewType] != null) {
            throw IllegalArgumentException("proxy for $viewType is already exist")
        }
        proxys.put(viewType, itemViewProxy)
        return this
    }

    fun removeProxy(itemViewProxy: ItemViewProxy<T>): ItemViewProxyManager<T> {
        val indexToRemove = proxys.indexOfValue(itemViewProxy)
        if (indexToRemove >= 0)
            proxys.removeAt(indexToRemove)
        return this
    }

    fun removeProxy(viewType: Int): ItemViewProxyManager<T> {
        val indexToRemove = proxys.indexOfKey(viewType)
        if (indexToRemove >= 0)
            proxys.removeAt(indexToRemove)
        return this
    }

    /**
     * 获取item对应的viewtype
     */
    fun getItemViewType(item: T, position: Int): Int {

        val count = proxys.size()

        for (i in 0 until count) {
            val proxy = proxys.valueAt(i)
            if (proxy.isMyViewType(item, position)) {
                return proxys.keyAt(i)
            }
        }
        throw IllegalArgumentException("there is no proxy that match whit the item for position $position")
    }

    /**
     * 转换数据
     */
    fun convert(holder: ViewHolder, item: T, position: Int) {
        val viewType = getItemViewType(item, position)
        val proxy = proxys[viewType]
        proxy?.convert(holder, item, position)
    }

    fun getProxy(viewType: Int): ItemViewProxy<T> {
        return proxys[viewType]!!
    }

    fun getItemViewLayoutId(viewType: Int): Int = getProxy(viewType).getItemViewLayouId()

    fun getItemViewType(itemViewProxy: ItemViewProxy<T>): Int = proxys.keyAt(proxys.indexOfValue(itemViewProxy))

}





























