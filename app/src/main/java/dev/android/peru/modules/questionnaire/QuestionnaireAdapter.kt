package dev.android.peru.modules.questionnaire

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import dev.android.peru.R
import kotlinx.android.synthetic.main.row_question_choice.view.*
import peru.android.dev.androidutils.inflate
import peru.android.dev.androidutils.updateVisibility
import peru.android.dev.datamodel.Choice
import peru.android.dev.datamodel.Question
import java.lang.IllegalArgumentException
import kotlin.properties.Delegates

class QuestionnaireAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: Question? by Delegates.observable<Question?>(initialValue = null, onChange = { _, _, _ ->
        setupRows(data)
        notifyDataSetChanged()
    })

    private var rows: List<Row> = listOf()

    private enum class RowTypes { Text, Numeric, SelectableChoice }

    private sealed class Row(val type: RowTypes) {
        object Text: Row(RowTypes.Text)
        object Numeric: Row(RowTypes.Numeric)
        class SelectableChoice(val choice: Choice): Row(RowTypes.SelectableChoice)
    }

    private fun setupRows(question: Question?) {
        rows = when(question) {
            is Question.Text -> listOf(Row.Text)
            is Question.Numeric -> listOf(Row.Numeric)
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
            RowTypes.SelectableChoice -> SelectableChoiceViewHolder.newInstance(
                    parent, onSelected = { notifyDataSetChanged() })
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row: Row = rows[position]
        when(holder) {
            is TextQuestionViewHolder -> holder.bind(data as Question.Text)
            is NumericQuestionViewHolder -> holder.bind(data as Question.Numeric)
            is SelectableChoiceViewHolder -> holder.bind(data = row as Row.SelectableChoice, question = data)
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

        fun bind(question: Question.Text) {
            (itemView as EditText).setText(question.input)
            (itemView as EditText).addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    question.input = p0?.toString() ?: ""
                }
            })
        }
    }

    private class NumericQuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup): NumericQuestionViewHolder {
                val view = parent.inflate(R.layout.row_question_numeric, attachToRoot = false)
                return NumericQuestionViewHolder(view)
            }
        }

        fun bind(question: Question.Numeric) {
            (itemView as EditText).setText(question.input?.toString())
            (itemView as EditText).addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    question.input = p0?.toString()?.toIntOrNull()
                }
            })
        }
    }

    private class SelectableChoiceViewHolder(val onSelected: (Choice)->Unit,
                                             itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup, onSelected: (Choice)->Unit): SelectableChoiceViewHolder {
                val view = parent.inflate(R.layout.row_question_choice, attachToRoot = false)
                return SelectableChoiceViewHolder(onSelected, view)
            }
        }

        private val labelTextView = itemView.choiceLabelTextView
        private val selectedImageView = itemView.choiceSelectedImageView

        fun bind(data: Row.SelectableChoice, question: Question?) {
            with(data.choice) {
                labelTextView.text = label
                selectedImageView.updateVisibility(isSelected)
                itemView.setOnClickListener {
                    question?.update(choice = this)
                    onSelected(this)
                }
            }
        }

        private fun Question.update(choice: Choice) = when(this) {
            is Question.SingleChoice -> {
                choices.forEach { it.isSelected = (it.id == choice.id) }
            }
            is Question.MultiChoice -> {
                choice.isSelected = !choice.isSelected
            }
            else -> { /* do nothing */ }
        }

    }
}

