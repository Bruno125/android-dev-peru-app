package peru.android.dev.androidutils

import android.text.Html
import android.widget.TextView

fun TextView.setHtml(text: String) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        setText(Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY))
    } else {
        setText(Html.fromHtml(text))
    }
}