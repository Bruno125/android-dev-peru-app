package dev.android.peru.modules.questionnaire

import peru.android.dev.datamodel.Answer
import peru.android.dev.datamodel.Question

class ValidateAnswer() {

    fun execute(question: Question): Boolean = when(val answer = question.getAnswer()) {
        is Answer.TextAnswer -> {
            question.isOptional || answer.value.isNotBlank()
        }
        is Answer.NumericAnswer -> {
            (question as Question.Numeric).isBetweenBounds(answer)
        }
        is Answer.SingleChoiceAnswer -> {
            true
        }
        is Answer.MultiChoiceAnswer -> {
             question.isOptional || answer.choices.isNotEmpty()
        }
        null -> { question.isOptional }
    }
}