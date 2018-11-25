package dev.android.peru.data.repositories

import peru.android.dev.datamodel.Questionnaire

interface QuestionnaireRepo {
    suspend fun getQuestionnaireForMeetup(meetupId: String): Questionnaire?

    suspend fun saveResponse(questionnaire: Questionnaire): Boolean
}