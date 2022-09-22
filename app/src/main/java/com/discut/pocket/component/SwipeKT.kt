package com.discut.pocket.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.discut.pocket.R

public class SwipeKT(context: Context) : ItemTouchHelper.SimpleCallback(0, 0) {

    private val trashIcon = ContextCompat.getDrawable(context, R.drawable.finger_print)
    private val circleColor = ContextCompat.getColor(context, R.color.black)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = circleColor }
    private val reverseSurfaceColor = ContextCompat.getColor(context, R.color.theme_main)
    private val CIRCLE_ACCELERATION = 6f
    private val context = context
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        // We don't want to do anything unless the view is being swiped
        if (dX == 0f) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        val left = viewHolder.itemView.left.toFloat()
        val top = viewHolder.itemView.top.toFloat()
        val right = viewHolder.itemView.right.toFloat()
        val bottom = viewHolder.itemView.bottom.toFloat()
        // Android draws with the center of the axis in the top left corner
        val width = right - left
        val height = bottom - top
        // Saves the canvas state to restore it at the end
        val saveCount = c.save()

        var iconColor = circleColor
        // Limits the child view to the space left by the viewholder while its being swiped
        c.clipRect(left, top, left + dX, bottom)
        c.drawColor(ContextCompat.getColor(context, R.color.gray))

        // The percentage that the child view has moved in the X axis
        val progress = dX / width
        val swipeThreshold = getSwipeThreshold(viewHolder)
        val iconPopThreshold = swipeThreshold + 0.125f
        val iconPopFinishedThreshold = iconPopThreshold + 0.125f
        var circleRadius = 0f
        val iconScale: Float

        when (progress) {
            in 0f..swipeThreshold -> {
                iconScale = 1f - (progress * 0.2f)
            }
            else -> {
                // The radius is the progress relative to the swipeThreshold multiplied by the width and the acceleration
                // The usage of the width allows the radius to adapt to the different screen sizes dynamically in every device
                circleRadius = (progress - swipeThreshold) * width * CIRCLE_ACCELERATION
                iconColor = reverseSurfaceColor
                iconScale = when (progress) {
                    in iconPopThreshold..iconPopFinishedThreshold -> 1.2f - progress * 0.2f
                    else -> 1f
                }
            }
        }

        trashIcon?.let {
            // 64 is the padding of the icon, divided by 2 to get the center of the icon
            val centerInXAxis = left + 64 + it.intrinsicWidth / 2f
            val centerInYAxis = top + 64 + it.intrinsicHeight / 2f

            // Sets the position of the icon inside the child view
            it.setBounds(
                (centerInXAxis - it.intrinsicWidth * iconScale).toInt(),
                (centerInYAxis - it.intrinsicHeight * iconScale).toInt(),
                (centerInXAxis + it.intrinsicWidth * iconScale).toInt(),
                (centerInYAxis + it.intrinsicHeight * iconScale).toInt()
            )

            // Sets the color of the icon
            it.colorFilter = PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN)

            if (circleRadius > 0) {
                c.drawCircle(centerInXAxis, centerInYAxis, circleRadius, circlePaint)
            }
            it.draw(c)
        }

        c.restoreToCount(saveCount)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}



