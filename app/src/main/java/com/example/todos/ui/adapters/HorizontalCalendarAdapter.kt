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
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HorizontalCalendarAdapter(private val dates: List<Date>): RecyclerView.Adapter<HorizontalCalendarAdapter.CalendarViewHolder>() {

    private var onClickListener :OnClickListener? = null

    private var selectedItem = RecyclerView.NO_POSITION
    private val currentCalendar = Calendar.getInstance()
    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    inner class CalendarViewHolder(val binding : ItemCalendarDateBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // Click listener for the whole item view
            itemView.setOnClickListener {
                val previousSelected = selectedItem
                selectedItem = adapterPosition
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedItem)

                // Notify listener of the click event
                onClickListener?.onDateClicked(dates[adapterPosition])
            }
        }
        fun bind(date: Date) {
            val formattedDayOfWeek = SimpleDateFormat("EEE", Locale.getDefault()).format(date)
            val formattedDayOfMonth = SimpleDateFormat("dd", Locale.getDefault()).format(date)

            binding.tvCalendarDate.text = formattedDayOfMonth.format(date)
            binding.tvCalendarDay.text = formattedDayOfWeek.format(date)

            if (isSameDay(date, currentCalendar.time)) {
                // Set selected state
                binding.linearCalendar.setBackgroundResource(R.drawable.rectangle_fill)
                binding.tvCalendarDate.setTextColor(Color.WHITE)
                binding.tvCalendarDay.setTextColor(Color.WHITE)
            } else {
                // Set default state
                binding.linearCalendar.setBackgroundResource(R.drawable.rectangle_outline)
                binding.tvCalendarDate.setTextColor(Color.BLACK)
                binding.tvCalendarDay.setTextColor(Color.BLACK)
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

    fun performClickOnCurrentDate() {
        val currentItemPosition = findCurrentDatePosition()
        if (currentItemPosition != RecyclerView.NO_POSITION) {
            onClickListener?.onDateClicked(dates[currentItemPosition])
        }
    }

    private fun findCurrentDatePosition(): Int {
        for (i in dates.indices) {
            if (isSameDay(dates[i], currentCalendar.time)) {
                return i
            }
        }
        return RecyclerView.NO_POSITION // Not found
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

}