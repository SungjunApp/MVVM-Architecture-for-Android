package com.sjsoft.workmanagement.shift.work

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sjsoft.workmanagement.GlideRequest
import com.sjsoft.workmanagement.R
import com.sjsoft.workmanagement.data.Shift
import java.util.*

class WorkAdapter(val context:Context, val requestBuilder: GlideRequest<Drawable>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal var list: ArrayList<Shift> = java.util.ArrayList()

    fun getList(): ArrayList<Shift> {
        return list
    }

    fun setList(newList: List<Shift>) {
        if (itemCount > 0) {
            val size = list.size
            list.clear()
            notifyItemRangeRemoved(0, size)
        }

        list.addAll(newList)
        notifyItemRangeInserted(0, list.size)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.adapter_work_shift, viewGroup, false)
        return ShiftViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShiftViewHolder) {
            val shift = list[position]


            holder.tv_start_time.text = String.format(context.getString(R.string.check_in_at_x),shift.getStartTime())
            holder.tv_duration.text = String.format(context.getString(R.string.worked_for_x),shift.getDuration())
            holder.tv_end_time.text = String.format(context.getString(R.string.check_out_at_x),shift.getEndTime())

            val visibility = if (shift.end.isEmpty())
                View.INVISIBLE
            else
                View.VISIBLE

            holder.v_duration.visibility = visibility
            holder.tv_end_time.visibility = visibility
            //holder.tv_end_location.visibility = visibility

            requestBuilder.clone()
                .load(shift.image)
                .fitCenter()
                .dontAnimate()
                .into(holder.imageview)
        }
    }

    inner class ShiftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imageview: ImageView = view.findViewById(R.id.imageview)
        internal var tv_start_time: TextView = view.findViewById(R.id.tv_start_time)
        internal var tv_duration: TextView = view.findViewById(R.id.tv_duration)
        internal var v_duration: View = view.findViewById(R.id.v_duration)
        internal var tv_end_time: TextView = view.findViewById(R.id.tv_end_time)

    }

}