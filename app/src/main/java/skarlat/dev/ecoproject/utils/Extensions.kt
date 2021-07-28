package skarlat.dev.ecoproject.utils

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.Disposable
import skarlat.dev.ecoproject.EcoTipsApp
import skarlat.dev.ecoproject.R
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

fun addDisposable(disposable: Disposable) {
    EcoTipsApp.addDisposable(disposable)
}

fun addDisposable(disposable: io.reactivex.disposables.Disposable) {
    EcoTipsApp.addDisposable(disposable)
}

fun RecyclerView.Adapter<RecyclerView.ViewHolder>.inflate(@LayoutRes layoutId: Int, parent: ViewGroup) : View {
    return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
}

fun RecyclerView.ViewHolder.getDrawableWithIdentifier(identifier: String) {
    val defType = "drawable"
}

fun <T>List<T>.itemAfter(item : T) : T? {
    val itemPosition = indexOf(item)
    if (itemPosition + 1 >= size) {
        return null
    }
    return this[itemPosition + 1]
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun Context.getQuantityStringZero(@PluralsRes plurals: Int, @StringRes zeroStringId: Int, quantity: Int) : String {
    return if (quantity == 0) {
        getString(zeroStringId, quantity)
    } else {
        resources.getQuantityString(plurals, quantity, quantity)
    }
}

fun ViewBinding.showSnackBar(message: String) {
    Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
}