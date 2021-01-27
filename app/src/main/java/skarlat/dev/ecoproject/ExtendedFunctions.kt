package skarlat.dev.ecoproject

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

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

fun View.visible(show: Boolean = false) {
    visibility = if (show) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}
fun ImageView.setImageFromAssets(assetManager: AssetManager, path: String) {
    try {
        val drawable: Drawable = BitmapDrawable(assetManager.open(path))
        setImageDrawable(drawable)
    }
    catch (error: Exception) {
        error.printStackTrace()
        setImageResource(R.drawable.lvl_1)
    }
}

fun RecyclerView.ViewHolder.getColor(@ColorRes colorId : Int, theme : Resources.Theme? = null)
        = ResourcesCompat.getColor(itemView.resources, colorId, theme)