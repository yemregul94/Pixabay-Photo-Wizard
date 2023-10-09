package com.moonlight.tsoft_pixabay.util

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.tsoft_pixabay.R

class SwipeDecorator {
    fun decorateSwiper(itemView: View, c: Canvas, dX:Float, recyclerView: RecyclerView){
        val context = recyclerView.context

        val backgroundCornerOffset = c.width/16
        val iconMargin = 16
        val background = ColorDrawable(Color.TRANSPARENT)

        if (dX > 0) { // Swiping to the right

            background.setBounds(itemView.left +dX.toInt() + backgroundCornerOffset, itemView.top, itemView.left, itemView.bottom)
            background.draw(c)

            val icon = ActivityCompat.getDrawable(recyclerView.context, R.drawable.ic_favorite_true)
            val iconTop = (itemView.height / 2) - (icon!!.intrinsicHeight / 2) + itemView.top
            icon.setTint(context.resources.getColor(R.color.white))

            icon.setBounds(iconMargin, iconTop , iconMargin + icon.intrinsicWidth, iconTop + icon.intrinsicHeight)
            background.color = context.resources.getColor(R.color.theme_primary)
            background.draw(c)
            icon.draw(c)


        } else if (dX < 0) { // Swiping to the left

            background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom)
            background.draw(c)

            val icon = ActivityCompat.getDrawable(recyclerView.context, R.drawable.ic_favorite_true)
            val iconTop = (itemView.height / 2) - (icon!!.intrinsicHeight / 2) + itemView.top
            icon.setTint(context.resources.getColor(R.color.white))

            icon.setBounds(itemView.width - icon.intrinsicWidth - iconMargin , iconTop , itemView.width - iconMargin, iconTop + icon.intrinsicHeight)
            background.color = context.resources.getColor(R.color.blue)
            background.draw(c)
            icon.draw(c)
        }

    }
}