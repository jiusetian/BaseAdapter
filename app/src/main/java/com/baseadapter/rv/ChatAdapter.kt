package com.baseadapter.rv

import android.content.Context
import com.baseadapter.bean.ChatMessage
import com.lib_baseadapter.rvkt.MultiItemTypeAdapter

/**
 * Author：Alex
 * Date：2019/7/19
 * Note：
 */
class ChatAdapter(context: Context, datas: MutableList<ChatMessage>) :
    MultiItemTypeAdapter<ChatMessage>(context, datas) {

    companion object {
        /**
         * 未知
         */
        const val TYPE_UNKNOWN = 0
        /**
         * 文本
         */
        const val TYPE_RECEIVER_TXT = 1
        const val TYPE_SEND_TXT = 2
        /**
         * 图片
         */
        const val TYPE_SEND_IMAGE = 3
        const val TYPE_RECEIVER_IMAGE = 4
        /**
         * 语音
         */
        const val TYPE_SEND_VOICE = 5
        const val TYPE_RECEIVER_VOICE = 6

    }

    init {
        addItemViewProxy(TYPE_SEND_TXT, SendTxtProxy())
        addItemViewProxy(TYPE_SEND_IMAGE,SendImageProxy())
        addItemViewProxy(TYPE_SEND_VOICE,SendVoiceProxy())
        addItemViewProxy(TYPE_RECEIVER_TXT,ReceiveTxtProxy())
        addItemViewProxy(TYPE_RECEIVER_IMAGE,ReceiveImageProxy())
        addItemViewProxy(TYPE_RECEIVER_VOICE,ReceiveVoiceProxy())
        addItemViewProxy(TYPE_UNKNOWN,UnknowTypeProxy())
    }


}