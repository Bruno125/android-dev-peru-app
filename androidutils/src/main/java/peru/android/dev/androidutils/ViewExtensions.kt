package peru.android.dev.androidutils

import android.view.View
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun View.updateVisibility(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.INVISIBLE
}

fun View.hideKeyboard() {
    val manager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    manager?.hideSoftInputFromWindow(rootView.windowToken, 0)
}