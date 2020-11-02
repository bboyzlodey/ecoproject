package skarlat.dev.ecoproject

import android.content.Context
import android.util.TypedValue
import android.view.View

fun Int.pxToDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

fun Int.dpToPx(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX, this.toFloat(), context.resources.displayMetrics
).toInt()

fun View.show(show: Boolean = false) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.GONE
    }
}