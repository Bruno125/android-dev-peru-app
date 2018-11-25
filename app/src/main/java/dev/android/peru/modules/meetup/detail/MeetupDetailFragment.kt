package dev.android.peru.modules.meetup.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import dev.android.peru.R
import dev.android.peru.provide
import peru.android.dev.baseutils.exhaustive
import dev.android.peru.modules.meetup.detail.MeetupUiState.*
import kotlinx.android.synthetic.main.meetup_detail_fragment.*
import peru.android.dev.datamodel.Meetup

class MeetupDetailFragment : Fragment() {

    companion object {
        fun newInstance() = MeetupDetailFragment()
    }

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
        meetupDescriptionTextView.text = meetup.description
    }

    private fun loadMeetup() {
        val meetupId = "wzJ9vGOmfO1vUDDs9qi6"
        viewModel.loadMeetup(meetupId)
    }
}
