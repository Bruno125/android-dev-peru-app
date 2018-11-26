package dev.android.peru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import dev.android.peru.modules.meetup.detail.MeetupDetailFragment
import dev.android.peru.modules.meetup.markAttendance.MarkAttendanceFragment
import dev.android.peru.modules.questionnaire.QuestionnaireFragment
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MeetupDetailFragment.Owner,
        PickQuestionnaireUserFragment.Owner{

    private var isMasterDetail = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isMasterDetail = findViewById<View>(R.id.mainFrameLayout) == null
        initFlow()
    }

    private fun initFlow() {
        val fragment = MeetupDetailFragment.newInstance()
        if(isMasterDetail) {
            leftFrameLayout?.replace(fragment)
        } else {
            mainFrameLayout?.replace(fragment)
        }
    }

    private fun FrameLayout.replace(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(this.id, fragment)
                .commit()
    }

    override fun openMarkAttendance(meetupId: String) {
        val fragment = MarkAttendanceFragment.newInstance(meetupId)
        if(isMasterDetail){
            centerFrameLayout?.replace(fragment)
        } else {
            mainFrameLayout?.replace(fragment)
        }
    }

    override fun openRequestFeedback(meetupId: String) {
        val fragment = PickQuestionnaireUserFragment.newInstance(meetupId)
        if(isMasterDetail) {
            centerFrameLayout?.replace(fragment)
        } else {
            mainFrameLayout?.replace(fragment)
        }
    }

    override fun openQuestionnaire(meetupId: String) {
        val fragment = QuestionnaireFragment.newInstance(meetupId)
        if(isMasterDetail) {
            rightFrameLayout?.replace(fragment)
            mainConstraintLayout?.transition(R.layout.activity_main_questionnaire)
        } else {
            mainFrameLayout?.replace(fragment)
        }
    }

    private fun ConstraintLayout.transition(layoutId: Int) {
        TransitionManager.beginDelayedTransition(this)
        ConstraintSet().let{
            it.clone(this.context, layoutId)
            it.applyTo(this)
        }
    }
}
