package dev.android.peru.modules.questionnaire

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.android.peru.modules.questionnaire.QuestionnaireUiState.*

import dev.android.peru.R
import dev.android.peru.provide
import kotlinx.android.synthetic.main.questionnaire_fragment.*
import peru.android.dev.androidutils.toast
import peru.android.dev.baseutils.exhaustive

class QuestionnaireFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionnaireFragment()
    }

    private lateinit var viewModel: QuestionnaireViewModel
    private val adapter = QuestionnaireAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.questionnaire_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provide()
        bind()
    }

    private fun bind() {
        lifecycle.addObserver(viewModel)
        questionRecyclerView.adapter = adapter

        viewModel.state.observe(this, Observer { state ->
            state?.let { render(it) }
        })

        viewModel.error.observe(this, Observer { error ->
            error?.let { toast(it) }
        })

        stepperFooter.onBackListener = {
            adapter.data?.let { viewModel.onPreviousClicked(it) }
        }
        stepperFooter.onNextListener = {
            adapter.data?.let { viewModel.onNextClicked(it) }
        }
        stepperFooter.onFinishListener = {
            viewModel.onFinishedClicked()
        }
    }

    private fun render(state: QuestionnaireUiState) {
        when(state) {
            is Loading -> {  }
            is InProgress -> { displayCurrent(state) }
            is Finished -> {  }
        }.exhaustive
    }

    private fun displayCurrent(state: InProgress) = with(state) {
        adapter.data = current
        titleTextView.text = current.title
        activity?.title = getString(R.string.question_current_step, currentIndex + 1, totalQuestions)
        stepperFooter.totalSteps = totalQuestions
        stepperFooter.setCurrent(currentIndex, animated = false)
    }
}
