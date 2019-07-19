package com.lib_baseadapter.rvkt

import android.content.Context
import android.view.LayoutInflater
import com.library.baseadapter.rvkt.base.ItemViewProxy
import com.library.baseadapter.rvkt.base.ViewHolder

/**
 * Author：Alex
 * Date：2019/7/18
 * Note：
 */
abstract class CommonAdapter<T>(context: Context, layoutId: Int, datas: MutableList<T>)
    : MultiItemTypeAdapter<T>(context, datas) {

    protected val inflater by lazy { LayoutInflater.from(context) }

    init {
        addItemViewProxy(object : ItemViewProxy<T> {

            override fun getItemViewLayouId(): Int = layoutId

            override fun isMyViewType(item: T, position: Int): Boolean = true

            override fun convert(holder: ViewHolder, item: T, position: Int) {
                convertData(holder, item, position)
            }

        })

    }

    protected abstract fun convertData(viewHolder: ViewHolder, t: T, position: Int)
}