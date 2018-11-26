package dev.android.peru.modules.meetup.markAttendance

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dev.android.peru.modules.search.SearchUserFragment
import dev.android.peru.provide
import peru.android.dev.datamodel.Attendance

class MarkAttendanceFragment: SearchUserFragment() {

    companion object {
        fun newInstance(meetupId: String) = MarkAttendanceFragment().apply {
            arguments = Bundle().apply { putString(PARAM_MEETUP_ID, meetupId) }
        }
    }

    private lateinit var viewModel: MarkAttendanceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = provide()
        viewModel.loadMeetup(getMeetupId())
        bind()
    }

    private fun bind() {
        viewModel.message.observe(this, Observer { message ->
            AlertDialog.Builder(context)
                    .setMessage(message)
                    .show()
        })
        viewModel.attedanceUpdate.observe(this, Observer { attendance ->
            searchViewModel.update(attendance)
        })
    }

    override fun onAttendanceSelected(attendance: Attendance) {
        viewModel.onAttendanceSelected(attendance)
    }

}