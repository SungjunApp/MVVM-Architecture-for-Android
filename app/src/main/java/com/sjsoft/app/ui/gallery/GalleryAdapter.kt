package com.sjsoft.app.ui.gallery

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sjsoft.app.GlideApp
import com.sjsoft.app.R
import com.sjsoft.app.data.PXLPhotoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gallery.*

class GalleryAdapter(val context: Context, private val marginInfo: MarginInfo) :
    ListAdapter<PXLPhotoItem, GalleryAdapter.ItemHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_gallery, viewGroup, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val params = holder.img.layoutParams as ViewGroup.LayoutParams
        params.width = marginInfo.getRectangleSize(context, 3)
        params.height = marginInfo.getRectangleSize(context, 3)
        holder.img.layoutParams = params

        val repoItem = getItem(position)

        if(repoItem.photo!=null){
            GlideApp.with(context).asDrawable().clone()
                .load(repoItem.photo.thumbnailUrl)
                .centerCrop()
                .dontAnimate()
                .into(holder.img)
        }else{
            holder.img.setImageDrawable(null)
        }

    }

    inner class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<PXLPhotoItem>() {
            override fun areItemsTheSame(oldItem: PXLPhotoItem, newItem: PXLPhotoItem): Boolean =
                oldItem.idx == newItem.idx

            override fun areContentsTheSame(oldItem: PXLPhotoItem, newItem: PXLPhotoItem): Boolean =
                oldItem.idx == newItem.idx
        }
    }
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