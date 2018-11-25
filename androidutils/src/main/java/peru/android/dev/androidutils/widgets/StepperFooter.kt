package peru.android.dev.androidutils.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import peru.android.dev.androidutils.R
import peru.android.dev.androidutils.runTypedArray
import peru.android.dev.androidutils.tryToGetInteger

class StepperFooter @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val backButton: Button
    private val nextButton: Button
    private val progressBar: DottedProgressBar

    var onBackListener: (()->Unit)? = null
    var onNextListener: (()->Unit)? = null
    var onFinishListener: (()->Unit)? = null

    var totalSteps: Int = 1; set(value) {
        if(value > 0) {
            field = value
            setupSteps()
        }
    }

    var current: Int = 0; private set

    init {
        LayoutInflater.from(context).inflate(R.layout.ms_footer_stepper, this, true)
        backButton = findViewById(R.id.stepper_backNavigationButton)
        nextButton = findViewById(R.id.stepper_nextNavigationButton)
        progressBar = findViewById(R.id.stepper_dottedProgressBar)

        backButton.setOnClickListener {
            onBackListener?.invoke()
        }

        nextButton.setOnClickListener {
            if(isLastStep()) {
                onFinishListener?.invoke()
            } else {
                onNextListener?.invoke()
            }
        }

        attrs?.let { bind(it) }
    }

    fun setCurrent(value: Int, animated: Boolean = false) {
        current = value
        progressBar.setCurrent(value, shouldAnimate = animated)
        updateButtons()
    }

    fun moveForward() {
        if(!isLastStep()) {
            setCurrent(value = current + 1, animated = true)
        }
        updateButtons()
    }

    fun moveBackwards() {
        if (current != 0) {
            setCurrent(value = current - 1, animated = true)
        }
        updateButtons()
    }

    private fun updateButtons() {
        backButton.visibility = if(current == 0) View.INVISIBLE else View.VISIBLE
        nextButton.setText(if(isLastStep()) R.string.finish else R.string.next)
    }

    private fun setupSteps() {
        progressBar.setDotCount(totalSteps)
        current = 0
        updateButtons()
    }

    private fun isLastStep() = current == (totalSteps - 1)

    private fun bind(set: AttributeSet) = context.runTypedArray(set, R.styleable.StepperFooter) {
        tryToGetInteger(R.styleable.StepperFooter_totalSteps) {
            totalSteps = it
        }
        tryToGetInteger(R.styleable.StepperFooter_currentStep) {
            setCurrent(value = it - 1, animated = false)
        }
    }

}