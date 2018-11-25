package dev.android.peru.data.source.firebase

import com.google.gson.Gson
import peru.android.dev.datamodel.Answer
import peru.android.dev.datamodel.Attendance
import peru.android.dev.datamodel.Meetup
import peru.android.dev.datamodel.Question

object FirebaseParser {

    val gson by lazy { Gson() }

    fun toMeetupObject(meetupId: String, data: Map<String, Any>): Meetup? {
        val meetup: Meetup? = Gson().fromJson(gson.toJson(data), Meetup::class.java)
        return meetup?.copy(
                id = meetupId,
                questionnaire = meetup.questionnaire?.copy(meetupId = meetupId)
        )
    }

    fun toAttendance(data: List<Map<String, Any>>): List<Attendance> {
        return data.mapNotNull { gson.fromJson(gson.toJson(it), Attendance::class.java) }
    }

    fun toMapData(question: Question): Map<String, Any>?{
        return when(val answer = question.getAnswer()) {
            is Answer.TextAnswer -> mapOf(
                    "type" to "text",
                    "input" to answer.value
            )
            is Answer.NumericAnswer -> mapOf(
                    "type" to "numeric",
                    "input" to answer.value
            )
            is Answer.SingleChoiceAnswer -> mapOf(
                    "type" to "singleChoice",
                    "choices" to answer.choiceId
            )
            is Answer.MultiChoiceAnswer -> mapOf(
                    "type" to "multiChoice",
                    "choices" to answer.choices.joinToString(
                            separator = "|",
                            transform = { it.choiceId }
                    )
            )
            null -> null
        }
    }

}