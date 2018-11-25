package peru.android.dev.androidutils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: Int, duration: Int = Toast.LENGTH_SHORT)
        = Toast.makeText(context, message, duration).show()