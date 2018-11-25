package dev.android.peru.modules.questionnaire

import androidx.lifecycle.*
import dev.android.peru.R
import dev.android.peru.data.Injection
import dev.android.peru.data.QuestionnaireRepo
import dev.android.peru.modules.questionnaire.QuestionnaireUiState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import peru.android.dev.androidutils.coroutines.AppDispatcher
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider
import peru.android.dev.datamodel.Question
import peru.android.dev.datamodel.Questionnaire

class QuestionnaireViewModel(
        val repo: QuestionnaireRepo = Injection.questionnaireRepo,
        dispatcher: CoroutinesDispatcherProvider = AppDispatcher.Default
) : ViewModel(), LifecycleObserver {

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

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        _state.value = Loading
        loadQuestionnaire()
    }

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

    private fun loadQuestionnaire() = execute {
        val meetups = Injection.meetupsRepo.getMeetups()
        val result = repo.getQuestionnaireForMeetup(meetups.first().id)
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
        _state.value = InProgress(
                current = questionnaire.questions[currentIndex],
                totalQuestions = questionnaire.questions.size,
                currentIndex = currentIndex)
    }
}
