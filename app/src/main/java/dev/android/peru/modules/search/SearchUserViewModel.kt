package dev.android.peru.modules.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.android.peru.data.Injection
import dev.android.peru.data.repositories.AttendanceRepo
import dev.android.peru.modules.search.SearchUserUiState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.datamodel.Attendance

class SearchUserViewModel(
        private val repo: AttendanceRepo = Injection.attendanceRepo,
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(dispatcher.main + viewModelJob)

    private lateinit var allResults: List<Attendance>
    private lateinit var filteredResults: List<Attendance>

    private val _state = MutableLiveData<SearchUserUiState>()
    val state: LiveData<SearchUserUiState>
        get() = _state

    fun search(query: String) = execute {
        filteredResults = if(query.toIntOrNull() != null) {
            allResults.filter { it.id == query }
        } else {
            allResults.filter { it.name.contains(query) }
        }
        updateContent()
    }

    fun loadMeetup(meetupId: String) = execute {
        if(::allResults.isInitialized) return@execute

        _state.postValue(Loading)
        allResults = repo.getAttendance(meetupId)
        _state.postValue(PleaseSearch)
    }

    fun update(entry: Attendance) {
        allResults = allResults.replace(entry)
        filteredResults = filteredResults.replace(entry)
        updateContent()
    }

    private fun updateContent() {
        _state.postValue(when{
            filteredResults.isEmpty() -> NoResults
            else -> DisplayResults(filteredResults)
        })
    }

    private fun List<Attendance>.replace(entry: Attendance) =
            map { if(it.id == entry.id) entry else it }

    private fun execute(predicate: suspend ()->Unit) = uiScope.launch { predicate() }
}
