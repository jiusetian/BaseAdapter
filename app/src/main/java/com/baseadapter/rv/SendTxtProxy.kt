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
class SendTxtProxy : ItemViewProxy<ChatMessage> {

    override fun getItemViewLayouId(): Int {
        return R.layout.item_chat_send_txt
    }

    override fun isMyViewType(item: ChatMessage, position: Int): Boolean {
        return if (item.type == ChatAdapter.TYPE_SEND_TXT) true else false
    }

    override fun convert(holder: ViewHolder, item: ChatMessage, position: Int) {
        holder.setImageResource(R.id.iv_avatar, item.avatar)
        holder.setText(R.id.tv_message, item.body)
    }

}