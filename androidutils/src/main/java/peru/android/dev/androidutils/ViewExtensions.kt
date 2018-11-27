package peru.android.dev.androidutils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi

fun View.updateVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun View.reversedCenteredCircularReveal(duration: Long = 1000L,
                                        onAnimationEnd: () -> Unit = {}) {
    val centerX = width / 2
    val centerY = height / 2
    ViewAnimationUtils.createCircularReveal(this, centerX, centerY,
            width.toFloat(), 0f).apply {
        this.duration = duration
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                visibility = View.GONE
                onAnimationEnd()
            }
        })
        start()
    }
}