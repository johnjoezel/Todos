//package com.example.todos.others
//
//import android.content.Context
//import android.graphics.Canvas
//import android.graphics.drawable.ColorDrawable
//import android.graphics.drawable.Drawable
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.RecyclerView
//import android.graphics.Color
//import androidx.appcompat.app.AlertDialog
//import com.example.todos.R
//import com.example.todos.viewModels.TodoViewModel
//
//
//class SwipeToDeleteHelper(private val viewModel: TodoViewModel, private val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//    private val trashIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_trash) // Replace with your trash icon
//
//    private val background: ColorDrawable = ColorDrawable(Color.RED)
//
//
//    override fun onMove(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        target: RecyclerView.ViewHolder
//    ): Boolean {
//        return false
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//       val position = viewHolder.adapterPosition
//        val dialog = AlertDialog.Builder(context)
//            .setTitle("Are you sure you want delete?")
//            .setPositiveButton("OK"){_, _->
//                viewModel.deleteToDo(position)
//            }
//            .setNegativeButton("Cancel"){dialog, _ -> dialog.cancel()
//            }
//            .create()
//
//        dialog.show()
//
//    }
//
//
//    override fun onChildDraw(
//        c: Canvas,
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder,
//        dX: Float,
//        dY: Float,
//        actionState: Int,
//        isCurrentlyActive: Boolean
//    ) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//
//        val itemView = viewHolder.itemView
//        val iconMargin = (itemView.height - trashIcon!!.intrinsicHeight) / 2
//        val iconTop = itemView.top + (itemView.height - trashIcon.intrinsicHeight) / 2
//        val iconBottom = iconTop + trashIcon.intrinsicHeight
//
//        if (dX > 0) { // Swiping to the right
//            val iconLeft = itemView.left + iconMargin
//            val iconRight = itemView.left + iconMargin + trashIcon.intrinsicWidth
//            trashIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
//        } else if (dX < 0) { // Swiping to the left
//            val iconLeft = itemView.right - iconMargin - trashIcon.intrinsicWidth
//            val iconRight = itemView.right - iconMargin
//            trashIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
//            background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
//        }
//
//        background.draw(c)
//        trashIcon.draw(c)
//    }
//}