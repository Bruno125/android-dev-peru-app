package dev.android.peru.modules.meetup.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import dev.android.peru.data.Injection
import dev.android.peru.data.repositories.MeetupsRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.datamodel.Meetup
import dev.android.peru.modules.meetup.detail.MeetupUiState.*

class MeetupDetailViewModel(
        private val repo: MeetupsRepo = Injection.meetupsRepo,
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(dispatcher.main + viewModelJob)

    private val _state = MutableLiveData<MeetupUiState>()
    val state: LiveData<MeetupUiState>
        get() = _state

    private lateinit var meetup: Meetup

    fun loadMeetup(meetupId: String) = execute {
        if(::meetup.isInitialized) return@execute

        _state.postValue(Loading)
        meetup = repo.getMeetup(meetupId) ?: return@execute
        _state.postValue(Detail(meetup))
    }

    private fun execute(predicate: suspend ()->Unit) = uiScope.launch { predicate() }
}
