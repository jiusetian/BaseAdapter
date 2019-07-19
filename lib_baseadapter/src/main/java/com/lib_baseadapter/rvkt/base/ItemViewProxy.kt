package com.library.baseadapter.rvkt.base

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
interface ItemViewProxy<T> {

    fun getItemViewLayouId(): Int

    /**
     * 根据当前item判断是否为自己的ItemViewTtype
     */
    fun isMyViewType(item: T, position: Int): Boolean

    fun convert(holder: ViewHolder, item: T, position: Int)


}