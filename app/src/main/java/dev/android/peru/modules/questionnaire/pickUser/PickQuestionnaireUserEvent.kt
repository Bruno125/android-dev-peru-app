package dev.android.peru.modules.questionnaire.pickUser

import peru.android.dev.baseutils.Event
import peru.android.dev.baseutils.SingleEvent

sealed class PickQuestionnaireUserEvent(private val event: SingleEvent = SingleEvent()) {

    class ShowNotRegisteredWarning(val meetupId: String): PickQuestionnaireUserEvent()

    class OpenQuestionnaire(val meetupId: String): PickQuestionnaireUserEvent()

    fun consume(predicate: (PickQuestionnaireUserEvent)->Unit) {
        if(event.consumed) return
        predicate(this)
        event.consume()
    }
}