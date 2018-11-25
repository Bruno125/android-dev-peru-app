package peru.android.dev.datamodel.adapters

import com.google.gson.*
import peru.android.dev.datamodel.Question
import java.lang.reflect.Type


internal class QuestionsDeserializer: JsonDeserializer<List<Question>> {

    companion object {
        private const val TYPE_TEXT = "text"
        private const val TYPE_NUMERIC = "numeric"
        private const val TYPE_SINGLE_CHOICE = "singleChoice"
        private const val TYPE_MULTIPLE_CHOICE = "multiChoice"
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): List<Question> {
        val questions = mutableListOf<Question>()

        val gson = Gson()
        json?.asJsonArray?.forEach {
            val data = it?.asJsonObject
            val type = data?.get("type")?.asString
            val question = when(type) {
                TYPE_TEXT -> gson.parse<Question.Text>(data)
                TYPE_NUMERIC -> gson.parse<Question.Numeric>(data)
                TYPE_SINGLE_CHOICE -> gson.parse<Question.SingleChoice>(data)
                TYPE_MULTIPLE_CHOICE -> gson.parse<Question.MultiChoice>(data)
                else -> { null }
            }
            question?.let { questions.add(it) }
        }

        return questions
    }

    private inline fun <reified T> Gson.parse(obj: JsonObject): T? {
        return fromJson(obj.toString(), T::class.java)
    }

}