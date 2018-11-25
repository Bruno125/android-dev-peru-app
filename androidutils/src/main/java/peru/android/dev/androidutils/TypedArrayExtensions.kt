package peru.android.dev.androidutils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

fun Context.runTypedArray(set: AttributeSet, attrs: IntArray, predicate: TypedArray.()->Unit) {
    val styledAttrs = theme.obtainStyledAttributes(set, attrs, 0, 0)
    styledAttrs?.predicate()
    styledAttrs?.recycle()
}


/**
 * A function that will execute the predicate block only if the TypedArray does
 * have a value (it != -1) for the required index.
 * @param index style index for a given styled typed array
 * @param predicate block to be executed in case there is a value
 */
fun TypedArray.tryToGetInteger(index: Int, predicate: (Int)->Unit) {
    val value = getInt(index, -1)
    hasValue(index)
    if(value != -1) {
        predicate(value)
    }
}