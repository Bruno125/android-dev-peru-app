package dev.android.peru.modules.questionnaire

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.android.peru.R
import dev.android.peru.modules.questionnaire.QuestionnaireUiState.*
import dev.android.peru.provide
import kotlinx.android.synthetic.main.questionnaire_fragment.*
import peru.android.dev.androidutils.toast
import peru.android.dev.baseutils.exhaustive

class QuestionnaireFragment : Fragment() {

    interface Owner {
        fun closeQuestionnaire()
    }

    companion object {
        private const val PARAM_MEETUP_ID = "PARAM_MEETUP_ID"

        fun newInstance(meetupId: String) = QuestionnaireFragment().apply {
            arguments = Bundle().apply { putString(PARAM_MEETUP_ID, meetupId) }
        }
    }

    private lateinit var owner: Owner
    private lateinit var viewModel: QuestionnaireViewModel
    private val adapter = QuestionnaireAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.questionnaire_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provide()
        viewModel.loadQuestionnaire(getMeetupId())
        bind()

    }

    private fun bind() {
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
        questionnaireToolbar.setNavigationOnClickListener {
            owner.closeQuestionnaire()
        }
    }

    private fun render(state: QuestionnaireUiState) {
        when(state) {
            is Loading -> {  }
            is InProgress -> { displayCurrent(state) }
            is Finished -> {  }
        }.exhaustive
    }

    protected fun getMeetupId(): String {
        return arguments?.getString(PARAM_MEETUP_ID) ?: ""
    }

    private fun displayCurrent(state: InProgress) = with(state) {
        adapter.data = current
        titleTextView.text = current.title
        activity?.title = getString(R.string.question_current_step, currentIndex + 1, totalQuestions)
        stepperFooter.totalSteps = totalQuestions
        stepperFooter.setCurrent(currentIndex, animated = false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        owner = context as? Owner ?: throw RuntimeException("$context must implement QuestionnaireFragment.Owner")
    }
}
