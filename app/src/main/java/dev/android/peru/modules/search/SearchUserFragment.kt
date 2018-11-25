package dev.android.peru.modules.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer

import dev.android.peru.R
import dev.android.peru.provide
import peru.android.dev.baseutils.exhaustive
import dev.android.peru.modules.search.SearchUserUiState.*
import kotlinx.android.synthetic.main.search_user_fragment.*
import peru.android.dev.androidutils.toast

class SearchUserFragment : Fragment() {

    companion object {
        fun newInstance() = SearchUserFragment()
    }

    private val adapter = AttendanceAdapter()
    private lateinit var viewModel: SearchUserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = provide()
        loadMeetup()
        bind()
    }

    private fun bind() {
        attendanceRecyclerView.adapter = adapter

        viewModel.state.observe(this, Observer { state ->
            state?.let { render(it) }
        })

        userSearchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query ?: "")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    private fun render(state: SearchUserUiState) {
        when(state) {
            is Loading -> {
                attendanceRecyclerView.visibility = View.GONE
                attendanceProgressBar.visibility = View.VISIBLE
            }
            is PleaseSearch -> {
                attendanceRecyclerView.visibility = View.GONE
                attendanceProgressBar.visibility = View.GONE
            }
            is DisplayResults -> {
                attendanceRecyclerView.visibility = View.VISIBLE
                attendanceProgressBar.visibility = View.GONE
                adapter.data = state.users
            }
            is NoResults -> {
                attendanceRecyclerView.visibility = View.GONE
                attendanceProgressBar.visibility = View.GONE
                toast(message = R.string.error_searching_user)
            }
        }.exhaustive
    }

    private fun loadMeetup() {
        val meetupId = "wzJ9vGOmfO1vUDDs9qi6"
        viewModel.loadMeetup(meetupId)
    }
}
