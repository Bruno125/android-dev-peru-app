package dev.android.peru.modules.questionnaire

import androidx.lifecycle.*
import dev.android.peru.R
import dev.android.peru.data.Injection
import dev.android.peru.data.repositories.QuestionnaireRepo
import dev.android.peru.modules.questionnaire.QuestionnaireUiState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.datamodel.Question
import peru.android.dev.datamodel.Questionnaire

class QuestionnaireViewModel(
        private val repo: QuestionnaireRepo = Injection.questionnaireRepo,
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
) : ViewModel() {

    private lateinit var questionnaire: Questionnaire
    private var currentIndex = 0

    private val _state = MutableLiveData<QuestionnaireUiState>()
    val state: LiveData<QuestionnaireUiState>
        get() = _state

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(dispatcher.main + viewModelJob)

    fun onNextClicked(question: Question) {
        if(question.isAnswerValid()) {
            currentIndex += 1
            update()
        } else {
            _error.value = R.string.please_answer_question
        }
    }
    fun onPreviousClicked(question: Question) {
        currentIndex -= 1
        update()
    }

    fun onFinishedClicked() = execute {
        val success = repo.saveResponse(questionnaire)
        if(success) {
            _state.value = Finished
        } else {
            _error.value = R.string.error_saving_response
        }
    }

    fun loadQuestionnaire(meetupId: String) = execute {
        if(::questionnaire.isInitialized) return@execute

        _state.value = Loading
        val result = repo.getQuestionnaireForMeetup(meetupId)
        if(result == null) {
            _error.value = R.string.question_current_step
            return@execute
        }
        questionnaire =  result
        update()
    }

    private fun Question.isAnswerValid() = ValidateAnswer().execute(this)

    private fun execute(predicate: suspend ()->Unit) = uiScope.launch { predicate() }

    private fun update() {
        _state.postValue(InProgress(
                current = questionnaire.questions[currentIndex],
                totalQuestions = questionnaire.questions.size,
                currentIndex = currentIndex)
        )
    }
}
