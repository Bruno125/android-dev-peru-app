package peru.android.dev.datamodel

import com.google.gson.annotations.JsonAdapter
import peru.android.dev.datamodel.adapters.QuestionsDeserializer

data class Questionnaire @JvmOverloads constructor(
        val id: String = "",
        val title: String = "",
        val meetupId: String = "",
        @JsonAdapter(QuestionsDeserializer::class)
        val questions: List<Question> = emptyList()
)