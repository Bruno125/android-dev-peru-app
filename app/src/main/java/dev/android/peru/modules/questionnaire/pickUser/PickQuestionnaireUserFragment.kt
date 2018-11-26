package dev.android.peru.modules.questionnaire.pickUser

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dev.android.peru.R
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserEvent.OpenQuestionnaire
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserEvent.ShowNotRegisteredWarning
import dev.android.peru.modules.search.SearchUserFragment
import dev.android.peru.provide
import peru.android.dev.androidutils.toast
import peru.android.dev.baseutils.exhaustive
import peru.android.dev.datamodel.Attendance

class PickQuestionnaireUserFragment: SearchUserFragment() {

    interface Owner {
        fun openQuestionnaire(meetupId: String)
    }

    companion object {
        fun newInstance(meetupId: String) = PickQuestionnaireUserFragment().apply {
            arguments = Bundle().apply { putString(PARAM_MEETUP_ID, meetupId) }
        }
    }

    private lateinit var owner: Owner
    private lateinit var viewModel: PickQuestionnaireUserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = provide()
        viewModel.loadMeetup(getMeetupId())
        bind()
    }

    private fun bind() {
        viewModel.event.observe(this, Observer { event ->
            event.consume { when(it) {
                is OpenQuestionnaire -> { owner.openQuestionnaire(it.meetupId) }
                is ShowNotRegisteredWarning -> { showNotRegisteredWarning(it) }
            }.exhaustive }
        })
    }

    override fun onAttendanceSelected(attendance: Attendance) {
        viewModel.onAttendanceSelected(attendance)
    }

    private fun showNotRegisteredWarning(event: ShowNotRegisteredWarning) {
        AlertDialog.Builder(context)
                .setMessage(R.string.user_has_not_registered)
                .setPositiveButton(R.string.common_yes) { _, _ ->
                    owner.openQuestionnaire(event.meetupId)
                }
                .setNegativeButton(R.string.common_no) { _, _ -> /* do nothing */ }
                .show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        owner = context as? Owner ?: throw RuntimeException("$context must implement PickQuestionnaireUserFragment.Owner")
    }
}