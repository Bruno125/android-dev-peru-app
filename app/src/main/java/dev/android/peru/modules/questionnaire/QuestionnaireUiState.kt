package dev.android.peru.modules.questionnaire

import peru.android.dev.datamodel.Question

sealed class QuestionnaireUiState {

    object Loading: QuestionnaireUiState()

    data class InProgress(val current: Question,
                          val totalQuestions: Int,
                          val currentIndex: Int): QuestionnaireUiState()

    object Finished: QuestionnaireUiState()

}
