package dev.android.peru.modules.questionnaire

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import dev.android.peru.R
import kotlinx.android.synthetic.main.row_question_choice.view.*
import peru.android.dev.androidutils.updateVisibility
import peru.android.dev.androidutils.inflate
import peru.android.dev.datamodel.Choice
import peru.android.dev.datamodel.Question
import java.lang.IllegalArgumentException
import kotlin.properties.Delegates

class QuestionnaireAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: Question? by Delegates.observable<Question?>(initialValue = null, onChange = { _, _, _ ->
        setupRows(data)
    })

    private var rows: List<Row> = listOf()

    private enum class RowTypes { Text, Numeric, SelectableChoice }

    private sealed class Row(val type: RowTypes) {
        class Text(val input: String): Row(RowTypes.Text)
        class Numeric(val input: Int?): Row(RowTypes.Numeric)
        class SelectableChoice(val choice: Choice): Row(RowTypes.SelectableChoice)
    }

    private fun setupRows(question: Question?) {
        rows = when(question) {
            is Question.Text -> listOf(Row.Text(question.input))
            is Question.Numeric -> listOf(Row.Numeric(question.input))
            is Question.SingleChoice -> question.choices.map { Row.SelectableChoice(it) }
            is Question.MultiChoice -> question.choices.map { Row.SelectableChoice(it) }
            null -> emptyList()
        }
    }

    override fun getItemCount(): Int {
        return rows.size
    }

    override fun getItemViewType(position: Int): Int {
        return rows[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val type = RowTypes.values()[viewType]
        return when(type) {
            RowTypes.Text -> TextQuestionViewHolder.newInstance(parent)
            RowTypes.Numeric -> NumericQuestionViewHolder.newInstance(parent)
            RowTypes.SelectableChoice -> SelectableChoiceViewHolder.newInstance(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row: Row = rows[position]
        when(holder) {
            is TextQuestionViewHolder -> holder.bind(row as Row.Text)
            is NumericQuestionViewHolder -> holder.bind(row as Row.Numeric)
            is SelectableChoiceViewHolder -> holder.bind(row as Row.SelectableChoice)
            else -> throw IllegalArgumentException("Invalid ViewHolder $holder for QuestionnaireAdapter")
        }
    }

    private class TextQuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): TextQuestionViewHolder {
                val view = parent.inflate(R.layout.row_question_text, attachToRoot = false)
                return TextQuestionViewHolder(view)
            }
        }

        fun bind(data: Row.Text) {
            (itemView as EditText).setText(data.input)
        }
    }

    private class NumericQuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): NumericQuestionViewHolder {
                val view = parent.inflate(R.layout.row_question_numeric, attachToRoot = false)
                return NumericQuestionViewHolder(view)
            }
        }

        fun bind(data: Row.Numeric) {
            (itemView as EditText).setText(data.input?.toString())
        }
    }

    private class SelectableChoiceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): SelectableChoiceViewHolder {
                val view = parent.inflate(R.layout.row_question_choice, attachToRoot = false)
                return SelectableChoiceViewHolder(view)
            }
        }

        private val labelTextView = itemView.choiceLabelTextView
        private val selectedImageView = itemView.choiceSelectedImageView

        fun bind(data: Row.SelectableChoice) {
            with(data.choice) {
                labelTextView.text = label

                itemView.setOnClickListener {
                    isSelected = !isSelected
                    selectedImageView.updateVisibility(isSelected)
                }

                selectedImageView.updateVisibility(isSelected)
            }
        }

    }
}

