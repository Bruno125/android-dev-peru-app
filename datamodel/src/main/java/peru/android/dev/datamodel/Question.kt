package peru.android.dev.datamodel

interface IQuestion : CanBeAnswered {
    val id: String
    val title: String
    val isOptional: Boolean
}

sealed class Question: IQuestion {

    data class Text(override val id: String,
                    override val title: String,
                    override val isOptional: Boolean = false,
                    val minLength: Int = 0,
                    val maxLength: Int = Int.MAX_VALUE,
                    var input: String = ""): Question() {

        override fun getAnswer() = Answer.TextAnswer(input)
    }

    data class Numeric(override val id: String,
                       override val title: String,
                       override val isOptional: Boolean = false,
                       val min: Int = 0,
                       val max: Int = Int.MAX_VALUE,
                       val defaultValue: Int = min,
                       var input: Int? = null): Question() {

        override fun getAnswer(): Answer? {
            val number = input
            return if(number == null) null else Answer.NumericAnswer(number)
        }
    }

    data class SingleChoice(override val id: String,
                            override val title: String,
                            override val isOptional: Boolean = false,
                            val choices: List<Choice>): Question() {

        override fun getAnswer(): Answer? {
            val choiceId = choices.firstOrNull { it.isSelected }?.id
            return if (choiceId == null) null else Answer.SingleChoiceAnswer(choiceId)
        }
    }

    data class MultiChoice(override val id: String,
                           override val title: String,
                           override val isOptional: Boolean = false,
                           val choices: List<Choice>): Question() {

        override fun getAnswer() = Answer.MultiChoiceAnswer(choices = choices.asSequence()
                .filter { it.isSelected }
                .map { Answer.SingleChoiceAnswer(it.id) }
                .toList()
        )
    }


}