package dev.android.peru

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dev.android.peru.modules.meetup.detail.MeetupDetailViewModel
import dev.android.peru.modules.questionnaire.QuestionnaireViewModel
import dev.android.peru.modules.search.SearchUserViewModel
import dev.android.peru.modules.search.markAttendance.MarkAttendanceViewModel


object ViewModelInjection: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.assignableFrom<QuestionnaireViewModel>()) {
            QuestionnaireViewModel() as T
        } else if (modelClass.assignableFrom<SearchUserViewModel>()) {
            SearchUserViewModel() as T
        } else if (modelClass.assignableFrom<MeetupDetailViewModel>()) {
            MeetupDetailViewModel() as T
        } else if (modelClass.assignableFrom<MarkAttendanceViewModel>()) {
            MarkAttendanceViewModel() as T
        } else {
            throw RuntimeException("Unsupported ViewModel")
        }
    }

    private inline fun <reified T> Class<*>?.assignableFrom(): Boolean {
        return this?.isAssignableFrom(T::class.java) == true
    }
}


inline fun <reified T: ViewModel> FragmentActivity.provide(): T {
    return ViewModelProviders.of(this, ViewModelInjection).get(T::class.java)
}

inline fun <reified T: ViewModel> Fragment.provide(): T {
    return ViewModelProviders.of(this, ViewModelInjection).get(T::class.java)
}
