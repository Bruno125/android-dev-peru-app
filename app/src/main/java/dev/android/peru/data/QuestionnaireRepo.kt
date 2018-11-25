package dev.android.peru.data

import dev.android.peru.data.firebase.FirebaseParser
import dev.android.peru.data.firebase.FirebaseRepo
import peru.android.dev.datamodel.Questionnaire
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface QuestionnaireRepo {
    suspend fun getQuestionnaireForMeetup(meetupId: String): Questionnaire?
}

class FirebaseQuestionnaireRepo(): QuestionnaireRepo, FirebaseRepo {

    override suspend fun getQuestionnaireForMeetup(meetupId: String): Questionnaire? {
        return suspendCoroutine { cont ->
            readDocument(path = "meetups/$meetupId", onSuccess = {
                val data = it?.data ?: emptyMap()
                cont.resume(FirebaseParser.toMeetupObject(data)?.questionnaire)
            })
        }
    }

}