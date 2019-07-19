package com.library.baseadapter.rvkt.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*

/**
 * Author：Alex
 * Date：2019/7/17
 * Note：
 */
class ViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val views = SparseArray<View>()

    companion object {

        fun createViewHolder(context: Context, itemView: View): ViewHolder {
            val holder = ViewHolder(context, itemView)
            return holder
        }

        fun createViewHolder(context: Context, parent: ViewGroup, layoutId: Int): ViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutId, parent, false)
            val holder = ViewHolder(context, itemView)
            return holder
        }
    }

    fun getConvertView(): View = itemView

    /**
     * 通过viewId获取控件
     */
    fun <T : View> getView(viewId: Int): T {
        var view = views[viewId]

        if (view == null) {
            view = itemView.findViewById(viewId)
            views.put(viewId, view)
        }
        return view as T
    }


    /****以下为辅助方法*****/

    fun setText(viewId: Int, text: String): ViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(resId)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageBitmap(bitmap)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): ViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageDrawable(drawable)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): ViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, backgroundRes: Int): ViewHolder {
        val v = getView<View>(viewId)
        v.setBackgroundResource(backgroundRes)
        return this
    }

    fun setTextColor(viewId: Int, textColor: Int): ViewHolder {
        val v = getView<TextView>(viewId)
        v.setTextColor(textColor)
        return this
    }

    fun setTextColorRes(viewId: Int, textColorRes: Int): ViewHolder {
        return setTextColor(viewId, context.resources.getColor(textColorRes))
    }

    fun setAlpha(viewId: Int, value: Float): ViewHolder {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId).alpha = value
        } else {
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId).startAnimation(alpha)
        }
        return this
    }

    fun setVisible(viewId: Int, visible: Boolean): ViewHolder {
        val v = getView<View>(viewId)
        v.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun linkify(viewId: Int): ViewHolder {
        val v = getView<TextView>(viewId)
        Linkify.addLinks(v, Linkify.ALL)
        return this
    }

    fun setTypeface(typeface: Typeface, vararg viewIds: Int): ViewHolder {
        viewIds.map {
            val v = getView<TextView>(it)
            v.typeface = typeface
            v.paintFlags = v.paintFlags or Paint.SUBPIXEL_TEXT_FLAG
        }
        return this
    }

    fun setProgress(viewId: Int, progress: Int): ViewHolder {
        val v = getView<ProgressBar>(viewId)
        v.progress = progress
        return this
    }

    fun setProgress(viewId: Int, progress: Int, max: Int): ViewHolder {
        val v = getView<ProgressBar>(viewId)
        v.progress = progress
        v.max = max
        return this
    }

    fun setMax(viewId: Int, max: Int): ViewHolder {
        val v = getView<ProgressBar>(viewId)
        v.max = max
        return this
    }

    fun setRating(viewId: Int, rating: Float): ViewHolder {
        val v = getView<RatingBar>(viewId)
        v.rating = rating
        return this
    }

    fun setRating(viewId: Int, rating: Float, max: Int): ViewHolder {
        val v = getView<RatingBar>(viewId)
        v.rating = rating
        v.max = max
        return this
    }

    fun setTag(viewId: Int, tag: Any): ViewHolder {
        val v = getView<View>(viewId)
        v.tag = tag
        return this
    }

    fun setTag(viewId: Int, tag: Any, key: Int): ViewHolder {
        val v = getView<View>(viewId)
        v.setTag(key, tag)
        return this
    }

    fun setChecked(viewId: Int, check: Boolean): ViewHolder {
        val v = getView<CheckBox>(viewId)
        v.isChecked = check
        return this
    }

    /**
     * 关于事件的
     */

    fun setOnClickListener(viewId: Int, listener: (View) -> Unit): ViewHolder {
        val v = getView<View>(viewId)
        v.setOnClickListener(listener)
        return this
    }

    fun setOnTouchListener(viewId: Int, listener: (View, MotionEvent) -> Boolean): ViewHolder {
        val v = getView<View>(viewId)
        v.setOnTouchListener(listener)
        return this
    }


    fun setOnLongClickListener(viewId: Int, listener: (View) -> Boolean): ViewHolder {
        val v = getView<View>(viewId)
        v.setOnLongClickListener(listener)
        return this
    }

}






























