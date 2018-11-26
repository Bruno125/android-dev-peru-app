package dev.android.peru.modules.meetup.detail

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.android.peru.R
import dev.android.peru.modules.meetup.detail.MeetupUiState.Detail
import dev.android.peru.modules.meetup.detail.MeetupUiState.Loading
import dev.android.peru.provide
import kotlinx.android.synthetic.main.meetup_detail_fragment.*
import peru.android.dev.androidutils.load
import peru.android.dev.androidutils.setHtml
import peru.android.dev.baseutils.exhaustive
import peru.android.dev.datamodel.Meetup

class MeetupDetailFragment : Fragment() {

    interface Owner {
        fun openMarkAttendance(meetupId: String)
        fun openRequestFeedback(meetupId: String)
    }

    companion object {
        fun newInstance() = MeetupDetailFragment()
    }

    private lateinit var owner: Owner
    private lateinit var viewModel: MeetupDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.meetup_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provide()
        bind()
        loadMeetup()
        setupToolbar()
    }

    private fun bind() {
        viewModel.state.observe(this, Observer { state ->
            state?.let { render(it) }
        })
    }

    private fun render(state: MeetupUiState) {
        when(state) {
            is Loading -> {  }
            is Detail -> { display(state.meetup) }
        }.exhaustive
    }

    private fun display(meetup: Meetup) {
        meetupTitleTextView.text = meetup.name
        meetupDescriptionTextView.setHtml(meetup.description)
        meetupCoverImageView.load(meetup.cover)

        meetupDetailToolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.menu_start_attendance -> { owner.openMarkAttendance(meetup.id) }
                R.id.menu_start_questionnaire -> { owner.openRequestFeedback(meetup.id) }
            }
            true
        }
    }

    private fun setupToolbar() {
        with(meetupDetailToolbar) {
            navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_close_white_24dp)
            inflateMenu(R.menu.menu_meetup_detail)
            setNavigationOnClickListener {
                //todo
            }
        }
    }

    private fun loadMeetup() {
        val meetupId = "wzJ9vGOmfO1vUDDs9qi6"
        viewModel.loadMeetup(meetupId)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        owner = context as? Owner ?: throw RuntimeException("$context must implement MeetupDetailFragment.Owner")
    }
}
