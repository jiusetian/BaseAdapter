package com.baseadapter

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.adapter.rvkt.CommonAdapter
import com.library.baseadapter.rvkt.base.ViewHolder
import com.library.baseadapter.rvkt.wrapper.HeaderAndFooterWrapper
import com.library.baseadapter.rvkt.wrapper.LoadMoreWrapper

class CommonActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: CommonAdapter<String>

    private lateinit var headerAndFooterWrapper: HeaderAndFooterWrapper

    private lateinit var loadMoreWrapper: LoadMoreWrapper

    private val datas = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        //初始化数据
        initDatas()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        adapter = object : CommonAdapter<String>(this, R.layout.item_list, datas) {
            override fun convertData(viewHolder: ViewHolder, s: String, position: Int) {
                viewHolder.setText(
                    R.id.id_item_list_title,
                    "$s , ${viewHolder.adapterPosition} , ${viewHolder.layoutPosition}"
                )
            }
        }
        //添加头和脚布局
        initHeaderAndFooter()

        loadMoreWrapper = LoadMoreWrapper(headerAndFooterWrapper)
        loadMoreWrapper.setLoadMoreViewId(R.layout.default_loading)
        loadMoreWrapper.setOnLoadMoreListener {
            Handler().postDelayed({
                for (i in 'A'..'Z') {
                    datas.add(i + "")
                }
                loadMoreWrapper.notifyDataSetChanged()
            }, 2000)
        }

        recyclerView.adapter = loadMoreWrapper

    }


    private fun initDatas() {
        for (i in 'A'..'Z') {
            datas.add(i + "")
        }
    }

    private fun initHeaderAndFooter() {
        headerAndFooterWrapper = HeaderAndFooterWrapper(adapter)
        val tv1 = TextView(this)
        tv1.text = "我是头布局"
        val tv2 = TextView(this)
        tv2.text = "我是脚布局"
        headerAndFooterWrapper.addHeaderView(tv1)
        headerAndFooterWrapper.addFooterView(tv2)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recycler_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val i = item?.itemId
        when (i) {
            R.id.action_linear -> recyclerView.layoutManager = LinearLayoutManager(this)
            R.id.action_grid -> recyclerView.layoutManager = GridLayoutManager(this, 3)
            R.id.action_staggered -> recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        recyclerView.adapter = loadMoreWrapper
        return super.onOptionsItemSelected(item)
    }

}
