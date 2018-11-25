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

    private lateinit var attendance: List<Attendance>

    private val _state = MutableLiveData<SearchUserUiState>()
    val state: LiveData<SearchUserUiState>
        get() = _state

    fun search(query: String) = execute {
        val results = if(query.toIntOrNull() != null) {
            attendance.filter { it.id == query }
        } else {
            attendance.filter { it.name.contains(query) }
        }
        _state.postValue(when{
            results.isEmpty() -> NoResults
            else -> DisplayResults(results)
        })
    }

    fun loadMeetup(meetupId: String) = execute {
        if(::attendance.isInitialized) return@execute

        _state.postValue(Loading)
        attendance = repo.getAttendance(meetupId)
        _state.postValue(PleaseSearch)
    }

    private fun execute(predicate: suspend ()->Unit) = uiScope.launch { predicate() }
}
