package com.example.cryptocoins.ui.coins

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocoins.core.util.dpToPx

class CoinItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val childCount = parent.adapter!!.itemCount

        val margin = 16.0F.dpToPx(parent.context)

        if (position == 0) {
            if (position != childCount - 1) { // not last element
                outRect.bottom = margin / 2
            }
        } else if (position == childCount - 1) { // last element
            outRect.top = margin / 2
        } else {
            outRect.top = margin / 2
            outRect.bottom = margin / 2
        }
    }
}