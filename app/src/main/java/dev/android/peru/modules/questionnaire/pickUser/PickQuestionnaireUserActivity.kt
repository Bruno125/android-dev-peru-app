package dev.android.peru.modules.questionnaire.pickUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.android.peru.R
import dev.android.peru.modules.questionnaire.QuestionnaireActivity
import peru.android.dev.androidutils.start

class PickQuestionnaireUserActivity : AppCompatActivity(), PickQuestionnaireUserFragment.Owner {

    companion object {
        const val PARAM_MEETUP_ID = "PARAM_MEETUP_ID"

        fun start(activity: AppCompatActivity, meetupId: String) {
            activity.start<PickQuestionnaireUserActivity>(Bundle().apply {
                putString(PARAM_MEETUP_ID, meetupId)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_questionnaire_user)

        val meetupId = intent?.extras?.getString(PARAM_MEETUP_ID) ?: ""

        supportFragmentManager.beginTransaction()
                .add(R.id.pickQuestionnaireUserLayout, PickQuestionnaireUserFragment.newInstance(meetupId))
                .commit()
    }

    override fun openQuestionnaire(meetupId: String) {
        QuestionnaireActivity.start(this, meetupId)
    }
}
