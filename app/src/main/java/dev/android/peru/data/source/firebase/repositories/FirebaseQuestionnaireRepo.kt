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
                val questionnaire = meetup?.questionnaire?.copy(meetupId = meetupId)
                cont.resume(questionnaire)
            })
        }
    }

    override suspend fun saveResponse(questionnaire: Questionnaire): Boolean {
        return suspendCoroutine {cont ->
            val content = questionnaire.questions.associate {
                it.id to FirebaseParser.toMapData(it)
            }
            val path = "meetups/${questionnaire.meetupId}/feedback"

            db.collection(path).document().set(content)
                    .addOnSuccessListener { cont.resume(true) }
                    .addOnFailureListener { cont.resume(false) }
                    .addOnCanceledListener { cont.resume(false) }
        }
    }
}