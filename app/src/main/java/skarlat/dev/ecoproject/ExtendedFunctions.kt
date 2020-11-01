package skarlat.dev.ecoproject

import android.content.Context
import android.util.TypedValue

fun Int.pxToDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

fun Int.dpToPx(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX, this.toFloat(), context.resources.displayMetrics
).toInt()