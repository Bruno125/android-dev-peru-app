package dev.android.peru.modules.search.markAttendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.android.peru.R
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.androidutils.viewModel.CoroutineViewModel
import peru.android.dev.datamodel.Attendance

class MarkAttendanceViewModel(
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
): CoroutineViewModel(dispatcher) {

    private lateinit var meetupId: String

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int>
        get() = _message

    private val _attedanceUpdate = MutableLiveData<Attendance>()
    val attedanceUpdate: LiveData<Attendance>
        get() = _attedanceUpdate

    fun onAttendanceSelected(attendance: Attendance) = execute {
        val updated = UpdateAttendanceInteractor().execute(meetupId, attendance)
        _message.postValue(when {
            updated.didShowUp -> R.string.attendance_registered
            else -> R.string.attendance_unregistered
        })
        _attedanceUpdate.postValue(updated)
    }

    fun loadMeetup(id: String) {
        if(::meetupId.isInitialized) return
        meetupId = id
    }
}