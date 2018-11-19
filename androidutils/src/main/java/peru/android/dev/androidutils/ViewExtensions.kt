package peru.android.dev.androidutils

import android.view.View

fun View.updateVisibility(visible: Boolean) {
    visibility = if(visible) View.VISIBLE else View.INVISIBLE
}