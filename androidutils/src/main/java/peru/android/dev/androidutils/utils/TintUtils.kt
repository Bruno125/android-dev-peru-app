package peru.android.dev.androidutils.utils

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

/**
 * Tints TextView's text color and it's compound drawables
 * @param tintColor color state list to use for tinting
 */
fun TextView.tint(tintColor: ColorStateList) {
    setTextColor(tintColor)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        val drawables = compoundDrawablesRelative
        setCompoundDrawablesRelative(
                drawables[0].tint(tintColor),
                drawables[1].tint(tintColor),
                drawables[2].tint(tintColor),
                drawables[3].tint(tintColor))
    } else {
        val drawables = compoundDrawables
        setCompoundDrawables(
                drawables[0].tint(tintColor),
                drawables[1].tint(tintColor),
                drawables[2].tint(tintColor),
                drawables[3].tint(tintColor))
    }
}


/**
 * Tints a drawable with the provided color
 * @param color tint color
 * @return tinted drawable
 */
fun Drawable.tint(@ColorInt color: Int): Drawable? {
    setColorFilter(color, PorterDuff.Mode.SRC_IN)
    return this
}

/**
 * Tints a drawable with the provided color state list
 * @param color tint color state list
 * @return tinted drawable
 */
fun Drawable?.tint(color: ColorStateList): Drawable? {
    var drawable = this ?: return this

    drawable = DrawableCompat.unwrap(drawable)
    val bounds = drawable.bounds
    drawable = DrawableCompat.wrap(drawable)
    // bounds can be all set to zeros when inflating vector drawables in Android Support Library 23.3.0...
    if (bounds.right == 0 || bounds.bottom == 0) {
        if (drawable.intrinsicHeight != -1 && drawable.intrinsicWidth != -1) {
            bounds.right = drawable.intrinsicWidth
            bounds.bottom = drawable.intrinsicHeight
        } else {
            Log.w("TintUtils", "Cannot tint drawable because its bounds cannot be determined!")
            return DrawableCompat.unwrap(drawable)
        }
    }
    DrawableCompat.setTintList(drawable, color)
    drawable.bounds = bounds
    return drawable
}