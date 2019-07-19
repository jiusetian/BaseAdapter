package com.lib_baseadapter.rvkt.wrapper

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
class EmptyWrapper<T>(innerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1
    }

    private var emptyView: View? = null
    private var emptyViewId:Int = 0




    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}