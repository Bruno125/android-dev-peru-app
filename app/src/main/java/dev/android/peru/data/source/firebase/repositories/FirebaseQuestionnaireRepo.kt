package dev.android.peru.data.source.firebase.repositories

import dev.android.peru.data.repositories.QuestionnaireRepo
import dev.android.peru.data.source.firebase.FirebaseParser
import dev.android.peru.data.source.firebase.FirebaseRepo
import peru.android.dev.datamodel.Questionnaire
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class FirebaseQuestionnaireRepo(): QuestionnaireRepo, FirebaseRepo {

    override suspend fun getQuestionnaireForMeetup(meetupId: String): Questionnaire? {
        return suspendCoroutine { cont ->
            readDocument(path = "meetups/$meetupId", onSuccess = {
                val meetup = FirebaseParser.toMeetupObject(meetupId, data = it?.data ?: emptyMap())
                cont.resume(meetup?.questionnaire)
            })
        }
    }

}