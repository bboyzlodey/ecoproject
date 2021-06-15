package skarlat.dev.ecoproject

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import skarlat.dev.ecoproject.adapter.CardsViewAdapter
import skarlat.dev.ecoproject.includes.dataclass.EcoCard

class ConnectionCardDecorator : RecyclerView.ItemDecoration() {


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        for (i in 1 until parent.childCount - 1) {
            val viewBounds = Rect()
            val child = parent[i]
            parent.getDecoratedBoundsWithMargins(child, viewBounds)
            val drawable = child.resources.getDrawable(R.drawable.card_divider, null)
            val bottom = viewBounds.bottom - 4.dpToPx(EcoTipsApp.instance.baseContext.applicationContext)
            val top = bottom - drawable.intrinsicHeight
            val middle = (viewBounds.right - viewBounds.left) / 2
            val partOf = drawable.intrinsicWidth / 2
            val left = middle - partOf
            val right = middle + partOf
            val viewHolder = parent.findViewHolderForAdapterPosition(i)
            if (viewHolder is CardsViewAdapter.CourseCardViewHolder && viewHolder.state == EcoCard.Status.WATCHED) {
                drawable.state = IntArray(1).apply { this[0] = android.R.attr.state_enabled}
            } else {
                drawable.state = IntArray(1).apply { this[0] = android.R.attr.state_enabled.inv() }
            }
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val drawable = view.resources.getDrawable(R.drawable.card_divider, null)

        outRect.set(0, 0, 0, drawable.intrinsicHeight + ((2 * 4).dpToPx(view.context.applicationContext)))
    }
}