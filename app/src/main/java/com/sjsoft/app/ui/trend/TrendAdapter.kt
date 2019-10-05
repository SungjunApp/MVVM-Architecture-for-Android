package com.sjsoft.app.ui.trend

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjsoft.app.R
import com.sjsoft.app.room.Frequency
import com.sjsoft.app.room.Lotto
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_history.*
import java.util.ArrayList

class TrendAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: ArrayList<Frequency> = ArrayList()


    fun replaceList(newList: List<Frequency>) {
        if (list.isNotEmpty()) {
            val size = list.size
            list.clear()
            notifyItemRangeRemoved(0, size)
        }

        list.addAll(newList)
        notifyItemRangeInserted(0, newList.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_history, viewGroup, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemHolder) {

            holder.tv_count.text =
                String.format(
                    context.getString(R.string.no),
                    list[position].winNo
                )

            val timesRes = if (list[position].count > 1) {
                R.string.times
            } else {
                R.string.time
            }

            holder.tv_message.text =
                String.format(
                    context.getString(timesRes),
                    list[position].count
                )
        }
    }

    inner class ItemHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer
}