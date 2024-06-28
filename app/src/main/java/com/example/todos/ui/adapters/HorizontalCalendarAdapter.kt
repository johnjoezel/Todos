package com.example.todos.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todos.R
import com.example.todos.databinding.ItemCalendarDateBinding
import com.example.todos.ui.listeners.OnClickListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HorizontalCalendarAdapter(private val dates: List<Date>): RecyclerView.Adapter<HorizontalCalendarAdapter.CalendarViewHolder>() {

    private var onClickListener :OnClickListener? = null
    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    inner class CalendarViewHolder(val binding : ItemCalendarDateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(date: Date) {
            val formattedDayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(date)
            val formattedDayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)
            binding.tvCalendarDate.text = formattedDayOfMonth.format(date)
            binding.tvCalendarDay.text = formattedDayOfWeek.format(date)

            binding.linearCalendar.setOnClickListener{
                binding.linearCalendar.setBackgroundColor(Color.parseColor("#F26E56"))
                binding.tvCalendarDate.setTextColor(Color.WHITE)
                binding.tvCalendarDay.setTextColor(Color.WHITE)
                onClickListener?.onDateClicked(date)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(ItemCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(dates[position])
    }
}