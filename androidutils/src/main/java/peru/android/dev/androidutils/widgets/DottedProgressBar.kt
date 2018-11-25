package peru.android.dev.androidutils.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.RelativeLayout

import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import peru.android.dev.androidutils.R
import peru.android.dev.androidutils.runTypedArray
import peru.android.dev.androidutils.tryToGetInteger
import peru.android.dev.androidutils.utils.tint

/**
 * Implementation extracted from:
 * https://github.com/stepstone-tech/android-material-stepper
 *
 * An indicator displaying the current position in a list of items with dots.
 */
class DottedProgressBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    @ColorInt
    private var mUnselectedColor: Int = 0

    @ColorInt
    private var mSelectedColor: Int = 0

    private var mDotCount: Int = 0

    private var mCurrent: Int = 0

    private var contentAlignment: ContentAlignment = ContentAlignment.Center

    private lateinit var container: LinearLayout

    init {
        mSelectedColor = ContextCompat.getColor(context, R.color.ms_selectedColor)
        mUnselectedColor = ContextCompat.getColor(context, R.color.ms_unselectedColor)
        attrs?.let { bind(it) }
    }

    fun setUnselectedColor(@ColorInt unselectedColor: Int) {
        this.mUnselectedColor = unselectedColor
    }

    fun setSelectedColor(@ColorInt selectedColor: Int) {
        this.mSelectedColor = selectedColor
    }

    fun setDotCount(dotCount: Int) {
        this.mDotCount = dotCount
        removeAllViews()
        container = LinearLayout(context).apply {
            orientation = HORIZONTAL
        }
        for (i in 0 until dotCount) {
            val view = LayoutInflater.from(context).inflate(R.layout.ms_dot, this, false)
            container.addView(view)
        }
        addView(container, RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            this.addRule(when(contentAlignment) {
                ContentAlignment.Left -> RelativeLayout.ALIGN_PARENT_LEFT
                ContentAlignment.Center -> RelativeLayout.CENTER_HORIZONTAL
                ContentAlignment.Right -> RelativeLayout.ALIGN_PARENT_RIGHT
            })
        })
        setCurrent(0, false)
    }

    /**
     * Changes the currently selected dot and updates the UI accordingly
     * @param current the new currently selected dot
     * @param shouldAnimate true if the change should be animated, false otherwise
     */
    fun setCurrent(current: Int, shouldAnimate: Boolean) {
        this.mCurrent = current
        update(shouldAnimate)
    }

    private fun update(shouldAnimate: Boolean) {
        for (i in 0 until mDotCount) {
            if (i == mCurrent) {
                container.getChildAt(i).animate()
                        .scaleX(FULL_SCALE)
                        .scaleY(FULL_SCALE)
                        .setDuration((if (shouldAnimate) SCALE_ANIMATION_DEFAULT_DURATION else DURATION_IMMEDIATE).toLong())
                        .setInterpolator(DEFAULT_INTERPOLATOR)
                        .start()
                colorChildAtPosition(i, true)
            } else {
                container.getChildAt(i).animate()
                        .scaleX(HALF_SCALE)
                        .scaleY(HALF_SCALE)
                        .setDuration((if (shouldAnimate) SCALE_ANIMATION_DEFAULT_DURATION else DURATION_IMMEDIATE).toLong())
                        .setInterpolator(DEFAULT_INTERPOLATOR)
                        .start()
                colorChildAtPosition(i, false)
            }
        }
    }

    private fun colorChildAtPosition(i: Int, selected: Boolean) {
        container.getChildAt(i).background.tint(if (selected) mSelectedColor else mUnselectedColor)
    }

    private fun bind(set: AttributeSet) = context.runTypedArray(set, R.styleable.DottedProgressBar) {
        tryToGetInteger(R.styleable.DottedProgressBar_dotCount) {
            setDotCount(it)
        }
        tryToGetInteger(R.styleable.DottedProgressBar_contentAlignment) {
            contentAlignment = ContentAlignment.values()[it]
        }
    }

    companion object {

        private val FULL_SCALE = 1f
        private val HALF_SCALE = 0.5f
        private val DURATION_IMMEDIATE = 0
        private val SCALE_ANIMATION_DEFAULT_DURATION = 300

        private val DEFAULT_INTERPOLATOR = DecelerateInterpolator()
    }

    private enum class ContentAlignment {
        Left,
        Center,
        Right
    }

}