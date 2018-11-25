package dev.android.peru.data.source.mock.repositories

import dev.android.peru.data.repositories.QuestionnaireRepo
import peru.android.dev.datamodel.Choice
import peru.android.dev.datamodel.Question
import peru.android.dev.datamodel.Questionnaire

class MockQuestionnaireRepo: QuestionnaireRepo {

    override suspend fun getQuestionnaireForMeetup(meetupId: String): Questionnaire {
        return Questionnaire(id = "1", title = "Mocked Questionnaire", questions = listOf(
                Question.Numeric(id = "", title = "Edad?"),
                Question.Text(id = "", title = "Nombre?"),
                Question.SingleChoice(id = "", title = "Temas?", choices = listOf(
                        Choice(id = "1", label = "Android", isSelected = false),
                        Choice(id = "2", label = "Kotlin", isSelected = false),
                        Choice(id = "3", label = "Facebook", isSelected = false)
                )),
                Question.MultiChoice(id = "", title = "Comida?", choices = listOf(
                        Choice(id = "1", label = "Sandwiches", isSelected = false),
                        Choice(id = "2", label = "Cerveza", isSelected = false),
                        Choice(id = "3", label = "Bocaditos", isSelected = false)
                ))
        ))
    }

}
