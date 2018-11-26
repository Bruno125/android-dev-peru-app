package dev.android.peru.modules.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.peru.R
import peru.android.dev.androidutils.start

class QuestionnaireActivity : AppCompatActivity(), QuestionnaireFragment.Owner {

    companion object {
        const val PARAM_MEETUP_ID = "PARAM_MEETUP_ID"

        fun start(activity: AppCompatActivity, meetupId: String) {
            activity.start<QuestionnaireActivity>(Bundle().apply {
                putString(PARAM_MEETUP_ID, meetupId)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        val meetupId = intent?.extras?.getString(PARAM_MEETUP_ID) ?: ""

        supportFragmentManager.beginTransaction()
                .add(R.id.questionnaireActivityLayout, QuestionnaireFragment.newInstance(meetupId))
                .commit()
    }

    override fun closeQuestionnaire() {
        finish()
    }
}
