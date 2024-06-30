package com.example.todos.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CalendarViewModel : ViewModel(){

    private val _currentMonthYear = MutableLiveData<String>()
    val currentMonthYear: LiveData<String> = _currentMonthYear

    private val _datesInMonth = MutableLiveData<List<Date>>()
    val datesInMonth: LiveData<List<Date>> = _datesInMonth

    private var calendar: Calendar = Calendar.getInstance()

    init {
        updateMonthYearText()
        updateDatesInMonth()
    }

    fun nextMonth() {
        calendar.add(Calendar.MONTH, 1)
        updateMonthYearText()
        updateDatesInMonth()
    }

    fun previousMonth() {
        calendar.add(Calendar.MONTH, -1)
        updateMonthYearText()
        updateDatesInMonth()
    }

    private fun updateMonthYearText() {
        val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        _currentMonthYear.value = sdf.format(calendar.time)
    }

    private fun updateDatesInMonth(){
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dates = mutableListOf<Date>()
        val cal = calendar.clone() as Calendar
        cal.set(Calendar.DAY_OF_MONTH, 1)

        val month = cal.get(Calendar.MONTH)
        while(cal.get(Calendar.MONTH) == month){
            dates.add(cal.time)
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }

        _datesInMonth.value = dates
    }
}