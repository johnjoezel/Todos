package com.example.todos.ui.adapters

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.databinding.ItemCalendarDateBinding
import com.example.todos.ui.horizontalcalendar.CalendarDateModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HorizontalCalendarAdapter(private val listener: (calendarDateModel: CalendarDateModel, position: Int) -> Unit) : RecyclerView.Adapter<HorizontalCalendarAdapter.CalendarViewHolder>() {

    var list = ArrayList<CalendarDateModel>()
    private var adapterPosition = -1

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    private var mListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    inner class CalendarViewHolder(val binding : ItemCalendarDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(ItemCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, @SuppressLint("RecyclerView") position: Int) {
       val itemList = list[position]
        holder.binding.tvCalendarDate.text = itemList.calendarDate
        holder.binding.tvCalendarDay.text = itemList.calendarDay

        val today = Calendar.getInstance().time
        val calendarDateModel = CalendarDateModel(today, isSelected = true)
        val currentposition = position
//        if(itemList.calendarDate == calendarDateModel.calendarDate){
//            val currentposition = position
//            if(position == (list.size - 1)){
//                holder.itemView.performClick()
//            }
//
//
////
//        }

        holder.itemView.setOnClickListener {
            adapterPosition = position
            notifyItemRangeChanged(0, list.size)
            mListener?.onItemClick(position)
            listener.invoke(itemList, position)
        }

        if (itemList.isSelected){
            holder.binding.tvCalendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.binding.tvCalendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.binding.linearCalendar.setBackgroundResource(R.drawable.rectangle_outline)
        }else {
            holder.binding.tvCalendarDay.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.binding.tvCalendarDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.binding.linearCalendar.setBackgroundResource(R.drawable.rectangle_outline)
        }
    }


    fun setData(calendarList: ArrayList<CalendarDateModel>) {
        list.clear()
        list.addAll(calendarList)
        notifyDataSetChanged()
    }

}