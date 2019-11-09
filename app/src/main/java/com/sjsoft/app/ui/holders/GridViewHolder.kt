package com.sjsoft.app.ui.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjsoft.app.GlideApp
import com.sjsoft.app.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gallery.*

class GridViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(imageUrl: String?, marginInfo: MarginInfo) {
        itemView.transitionName = "$adapterPosition$imageUrl"
        val params = img.layoutParams as ViewGroup.LayoutParams
        params.width = marginInfo.getRectangleSize(this.itemView.context, 3)
        params.height = marginInfo.getRectangleSize(this.itemView.context, 3)
        img.layoutParams = params

        if (imageUrl != null) {
            GlideApp.with(this.itemView.context).asDrawable().clone()
                .load(imageUrl)
                .centerCrop()
                .dontAnimate()
                .into(img)
        } else {
            img.setImageDrawable(null)
        }
    }

    companion object {
        fun create(parent: ViewGroup): GridViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_gallery, parent, false)
            return GridViewHolder(view)
        }
    }
}
