package skarlat.dev.ecoproject.utils

import android.content.Context
import android.util.TypedValue

object PixelUtils {
    fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()
}