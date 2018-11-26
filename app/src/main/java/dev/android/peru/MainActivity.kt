package dev.android.peru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.android.peru.modules.meetup.detail.MeetupDetailFragment
import dev.android.peru.modules.meetup.markAttendance.MarkAttendanceFragment
import dev.android.peru.modules.questionnaire.QuestionnaireFragment
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserFragment

class MainActivity : AppCompatActivity(),
        MeetupDetailFragment.Owner,
        PickQuestionnaireUserFragment.Owner{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replace(MeetupDetailFragment.newInstance())
    }

    fun replace(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFrameLayout, fragment)
                .commit()
    }

    override fun openMarkAttendance(meetupId: String) {
        replace(MarkAttendanceFragment.newInstance(meetupId))
    }

    override fun openRequestFeedback(meetupId: String) {
        replace(PickQuestionnaireUserFragment.newInstance(meetupId))
    }

    override fun openQuestionnaire(meetupId: String) {
        replace(QuestionnaireFragment.newInstance(meetupId))
    }
}
