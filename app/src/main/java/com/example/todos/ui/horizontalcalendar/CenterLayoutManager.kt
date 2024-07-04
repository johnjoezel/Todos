//package com.example.todos.ui.horizontalcalendar
//
//import android.content.Context
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
//import androidx.recyclerview.widget.LinearLayoutManager
//
//
//class CenterLayoutManager(context: Context) : LinearLayoutManager(context) {
//
//    private var currentItemPosition = -1
//
//    override fun findFirstCompletelyVisibleItemPosition(): Int {
//        val currentView = findViewByPosition(currentItemPosition)
//        return if (currentView != null && isFullyVisible(currentView)) currentItemPosition else NO_POSITION
//    }
//
//    fun setCurrentItemPosition(position: Int) {
//        currentItemPosition = position
//    }
//
//    private fun isFullyVisible(view: View): Boolean {
//        val parent = view.parent as ViewGroup
//        val childRect = Rect()
//        view.getDrawingRect(childRect)
//        return parent.isInView(childRect)
//    }
//
//}
//
