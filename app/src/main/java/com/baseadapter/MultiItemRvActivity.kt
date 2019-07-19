package com.baseadapter

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.baseadapter.bean.ChatMessage
import com.baseadapter.rv.ChatAdapter
import com.library.baseadapter.rvkt.wrapper.LoadMoreWrapper

class MultiItemRvActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    private lateinit var loadMoreWrapper: LoadMoreWrapper

    private val datas = ArrayList<ChatMessage>()


    //初始化数据
    init {
        datas.addAll(getDatas())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_item_rv)

        recyclerView = findViewById(R.id.id_recyclerview)

        recyclerView?.layoutManager = LinearLayoutManager(this)

        val chatAdapter = ChatAdapter(this, datas)
        loadMoreWrapper = LoadMoreWrapper(chatAdapter)
        loadMoreWrapper.setLoadMoreView(
            LayoutInflater.from(this).inflate(
                R.layout.default_loading,
                recyclerView,
                false
            )
        )

        loadMoreWrapper.setOnLoadMoreListener {
            Handler().postDelayed({
                datas.addAll(getDatas())
                loadMoreWrapper.notifyDataSetChanged()
            }, 2000)
        }
        recyclerView?.adapter = loadMoreWrapper

    }

    /**
     * 获取测试数据
     */
    private fun getDatas(): ArrayList<ChatMessage> {
        val datas = ArrayList<ChatMessage>()

        for (i in 0 until 7) {
            val chatMessage = ChatMessage()
            chatMessage.avatar = R.drawable.bg_my_header
            chatMessage.body = "你好"
            chatMessage.type = i
            datas.add(chatMessage)
        }

        for (i in 0 until 7) {
            val chatMessage = ChatMessage()
            chatMessage.avatar = R.drawable.default_header
            chatMessage.body = "你谁啊"
            chatMessage.type = i
            datas.add(chatMessage)
        }

        for (i in 0 until 7) {
            val chatMessage = ChatMessage()
            chatMessage.avatar = R.drawable.icon_logo_github
            chatMessage.body = "我是老铁"
            chatMessage.type = i
            datas.add(chatMessage)
        }
        return datas
    }
}
