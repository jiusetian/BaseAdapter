package com.baseadapter.rv

import com.baseadapter.R
import com.baseadapter.bean.ChatMessage
import com.library.baseadapter.rvkt.base.ItemViewProxy
import com.library.baseadapter.rvkt.base.ViewHolder

/**
 * Author：Alex
 * Date：2019/7/19
 * Note：
 */
class UnknowTypeProxy : ItemViewProxy<ChatMessage> {

    override fun getItemViewLayouId(): Int {
        return R.layout.item_unknown
    }

    override fun isMyViewType(item: ChatMessage, position: Int): Boolean {
        return if (item.type == ChatAdapter.TYPE_UNKNOWN) true else false
    }

    override fun convert(holder: ViewHolder, item: ChatMessage, position: Int) {
        //holder.setImageResource(R.id.iv_avatar,item.avatar)
    }
}