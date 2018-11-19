package peru.android.dev.androidutils

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(layout: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layout, this, attachToRoot)