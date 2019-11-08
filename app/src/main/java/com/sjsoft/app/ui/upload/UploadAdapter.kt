package com.sjsoft.app.ui.upload

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.sjsoft.app.GlideApp
import com.sjsoft.app.R
import com.sjsoft.app.data.PXLPhotoItem
import com.sjsoft.app.ui.holders.GridViewHolder
import com.sjsoft.app.ui.holders.MarginInfo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_gallery.*

class UploadAdapter(val context: Context, private val marginInfo: MarginInfo) :
    ListAdapter<S3ObjectSummary, GridViewHolder>(REPO_COMPARATOR) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder.create(viewGroup)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind("https://vocalhong.s3-ap-northeast-1.amazonaws.com/" + getItem(position).key, marginInfo)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<S3ObjectSummary>() {
            override fun areItemsTheSame(oldItem: S3ObjectSummary, newItem: S3ObjectSummary): Boolean =
                oldItem.key == newItem.key

            override fun areContentsTheSame(oldItem: S3ObjectSummary, newItem: S3ObjectSummary): Boolean =
                oldItem.key == newItem.key
        }
    }
}