package dev.android.peru.modules.questionnaire.pickUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.androidutils.viewModel.CoroutineViewModel
import peru.android.dev.datamodel.Attendance
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserEvent.*

class PickQuestionnaireUserViewModel(
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
): CoroutineViewModel(dispatcher) {

    private lateinit var meetupId: String

    private val _event = MutableLiveData<PickQuestionnaireUserEvent>()
    val event: LiveData<PickQuestionnaireUserEvent>
        get() = _event

    fun onAttendanceSelected(attendance: Attendance) = execute {
        if(!attendance.didShowUp) {
            _event.postValue(ShowNotRegisteredWarning(meetupId))
        } else {
            _event.postValue(OpenQuestionnaire(meetupId))
        }
    }

    fun loadMeetup(id: String) {
        if(::meetupId.isInitialized) return
        meetupId = id
    }
}