package com.sjsoft.app.ui.history

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.pixlee.pixleesdk.PXLPhoto
import com.sjsoft.app.GlideApp
import com.sjsoft.app.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gallery.*
import java.util.ArrayList

class GalleryAdapter(val context: Context, private val marginInfo: MarginInfo) :
    RecyclerView.Adapter<GalleryAdapter.ItemHolder>() {
    private var list: ArrayList<PXLPhoto> = ArrayList()


    fun replaceList(newList: List<PXLPhoto>) {
        if (list.isNotEmpty()) {
            val size = list.size
            list.clear()
            notifyItemRangeRemoved(0, size)
        }

        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_gallery, viewGroup, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val params = holder.img.layoutParams as ViewGroup.LayoutParams
        params.width = marginInfo.getRectangleSize(context, 3)
        params.height = marginInfo.getRectangleSize(context, 3)
        holder.img.layoutParams = params

        GlideApp.with(context).asDrawable().clone()
            .load(list[position].thumbnailUrl)
            .centerCrop()
            .dontAnimate()
            .into(holder.img)
    }

    inner class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}

data class MarginInfo(val side: Int
                      , val paddingTopBottom: Int
                      , val paddingLeftRight: Int) {
    fun getRectangleSize(context: Context, span: Int): Int {
        val wm: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)

        val edgeMargin = side * 2
        val sideMargin = paddingLeftRight * 2 * span
        return (size.x - edgeMargin - sideMargin) / span
    }
}