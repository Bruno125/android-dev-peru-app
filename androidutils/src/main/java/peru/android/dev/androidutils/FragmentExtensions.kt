package peru.android.dev.androidutils

import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.os.HandlerCompat.postDelayed



fun Fragment.toast(message: Int, duration: Int = Toast.LENGTH_SHORT)
        = Toast.makeText(context, message, duration).show()

fun Fragment.runWithDelay(delay: Long, predicate: ()->Unit) {
    val handler = Handler()
    handler.postDelayed({
        predicate()
    }, delay)
}