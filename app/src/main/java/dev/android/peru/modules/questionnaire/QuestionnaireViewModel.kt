package dev.android.peru.modules.questionnaire

import androidx.lifecycle.*
import dev.android.peru.modules.questionnaire.QuestionnaireUiState.*
import peru.android.dev.datamodel.Choice
import peru.android.dev.datamodel.Question
import peru.android.dev.datamodel.Questionnaire

class QuestionnaireViewModel : ViewModel(), LifecycleObserver {

    private lateinit var questionnaire: Questionnaire
    private var currentIndex = 0

    private val _state = MutableLiveData<QuestionnaireUiState>()
    val state: LiveData<QuestionnaireUiState>
        get() = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        _state.value = Loading
        loadQuestionnaire()
    }

    fun onNextClicked(question: Question) {

    }

    private fun loadQuestionnaire() { //TODO
        questionnaire = Questionnaire(id = "1", title = "Test Q", questions = listOf(
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
        update()
    }

    private fun update() {
        _state.value = InProgress(
                current = questionnaire.questions[currentIndex],
                totalQuestions = questionnaire.questions.size,
                currentIndex = currentIndex)
    }
}
