package peru.android.dev.datamodel

sealed class Answer {

    data class TextAnswer(val value: String): Answer()

    data class NumericAnswer(val value: Number): Answer()

    data class SingleChoiceAnswer(val choiceId: String): Answer()

    data class MultiChoiceAnswer(val choices: List<SingleChoiceAnswer>): Answer()

}