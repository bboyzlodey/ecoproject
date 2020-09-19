package skarlat.dev.ecoproject

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Класс который будет рисаовать отступ между вьюхолдерами
 */

class ConnectionDecorator(val width: Float, val height: Float, val colorId: Int) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.childCount
//        c.drawL
        TODO("Write Decoration")

        super.onDrawOver(c, parent, state)
    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        TODO("Написать отступ для декоратора")
        super.getItemOffsets(outRect, view, parent, state)
    }
}