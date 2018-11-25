package peru.android.dev.androidutils.widgets

import android.widget.ProgressBar
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Property
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import peru.android.dev.androidutils.R
import peru.android.dev.androidutils.tryToGetInteger
import peru.android.dev.androidutils.utils.tint

/**
 * Implementation extracted from:
 * https://github.com/stepstone-tech/android-material-stepper
 *
 * A {@link ProgressBar} which exposes methods for coloring primary progress and progress
 * background colors individually. It also allows to animate progress changes.
 */
class ColorableProgressBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : ProgressBar(context, attrs, defStyleAttr) {

    @ColorInt
    private var mProgressColor: Int = 0

    @ColorInt
    private var mProgressBackgroundColor: Int = 0

    init {
        mProgressColor = ContextCompat.getColor(context, R.color.ms_selectedColor)
        mProgressBackgroundColor = ContextCompat.getColor(context, R.color.ms_unselectedColor)
        super.setProgressDrawable(ContextCompat.getDrawable(context, R.drawable.ms_colorable_progress_bar))
        updateProgressDrawable()
        attrs?.let { bind(it) }
    }

    private fun bind(attrs: AttributeSet) {
        val styledAttrs = context.theme.obtainStyledAttributes(attrs, R.styleable.ColorableProgressBar, 0,0)

        styledAttrs.tryToGetInteger(R.styleable.ColorableProgressBar_progressCompat) {
            setProgressCompat(it, animate = false)
        }
    }

    override fun setProgressDrawable(d: Drawable) {
        // no-op
    }

    override fun setProgressDrawableTiled(d: Drawable) {
        // no-op
    }

    @Synchronized
    override fun setMax(max: Int) {
        super.setMax(max * PROGRESS_RANGE_MULTIPLIER)
    }

    fun setProgressColor(@ColorInt progressColor: Int) {
        this.mProgressColor = progressColor
        updateProgressDrawable()
    }

    fun setProgressBackgroundColor(@ColorInt backgroundColor: Int) {
        this.mProgressBackgroundColor = backgroundColor
        updateProgressDrawable()
    }

    fun setProgressCompat(progress: Int, animate: Boolean) {
        if (animate) {
            val animator = ObjectAnimator.ofInt(this, PROGRESS_PROPERTY, getProgress(), progress * PROGRESS_RANGE_MULTIPLIER)
            animator.duration = PROGRESS_ANIM_DURATION.toLong()
            animator.interpolator = PROGRESS_ANIM_INTERPOLATOR
            animator.start()
        } else {
            setProgress(progress * PROGRESS_RANGE_MULTIPLIER)
        }
    }

    private fun updateProgressDrawable() {
        val progressBarDrawable = progressDrawable as LayerDrawable
        val backgroundDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.background)
        val progressDrawable = progressBarDrawable.findDrawableByLayerId(android.R.id.progress)

        backgroundDrawable.tint(mProgressBackgroundColor)
        progressDrawable.tint(mProgressColor)
    }

    companion object {

        /**
         * Interpolator used for smooth progress animations.
         */
        private val PROGRESS_ANIM_INTERPOLATOR = DecelerateInterpolator()

        /**
         * Duration of smooth progress animations.
         */
        private const val PROGRESS_ANIM_DURATION = 200

        /**
         * A multiplier to be used when setting the current progress of this progress bar.
         * It is needed to animate the progress changes.
         */
        private const val PROGRESS_RANGE_MULTIPLIER = 100

        private val PROGRESS_PROPERTY = object : Property<ProgressBar, Int>(Int::class.java, "progress") {

            override operator fun set(progressBar: ProgressBar, value: Int?) {
                progressBar.progress = value ?: 0
            }

            override operator fun get(progressBar: ProgressBar): Int? {
                return progressBar.progress
            }
        }
    }

}