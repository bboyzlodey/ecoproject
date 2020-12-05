package skarlat.dev.ecoproject

import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView

class ConnectionDecorator(val width: Float, val height: Float, private val colorId: Int, val drawable: Drawable) : RecyclerView.ItemDecoration() {


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        c.save()
        for (i in 0 until parent.childCount - 1) {
            val viewBounds = Rect()
            val child = parent[i]
            parent.getDecoratedBoundsWithMargins(child, viewBounds)
            val bottom = viewBounds.bottom - 4.dpToPx(App.instance.baseContext.applicationContext)
            val top = bottom - drawable.intrinsicHeight
            val middle = (viewBounds.right - viewBounds.left) / 2
            val partOf = drawable.intrinsicWidth / 2
            val left = middle - partOf
            val right = middle + partOf
            // TODO Set color for drawable using colorId
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, drawable.intrinsicHeight + ((2 * 4).dpToPx(view.context.applicationContext)))
    }
}