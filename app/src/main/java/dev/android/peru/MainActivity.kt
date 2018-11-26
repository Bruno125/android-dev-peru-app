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
import dev.android.peru.modules.meetup.markAttendance.MarkAttendanceActivity
import dev.android.peru.modules.meetup.markAttendance.MarkAttendanceFragment
import dev.android.peru.modules.questionnaire.QuestionnaireFragment
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserActivity
import dev.android.peru.modules.questionnaire.pickUser.PickQuestionnaireUserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MeetupDetailFragment.Owner,
        PickQuestionnaireUserFragment.Owner,
        QuestionnaireFragment.Owner {

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

    override fun openMarkAttendance(meetupId: String) {
        if(isMasterDetail){
            val fragment = MarkAttendanceFragment.newInstance(meetupId)
            centerFrameLayout?.replace(fragment)
        } else {
            MarkAttendanceActivity.start(this, meetupId)
        }
    }

    override fun openRequestFeedback(meetupId: String) {
        if(isMasterDetail) {
            val fragment = PickQuestionnaireUserFragment.newInstance(meetupId)
            centerFrameLayout?.replace(fragment)
        } else {
            PickQuestionnaireUserActivity.start(this, meetupId)
        }
    }

    override fun openQuestionnaire(meetupId: String) {
        if(isMasterDetail) {
            val fragment = QuestionnaireFragment.newInstance(meetupId)
            rightFrameLayout?.replace(fragment)
            mainConstraintLayout?.transition(R.layout.activity_main_questionnaire)
        } else {
            // do nothing
        }
    }

    override fun closeQuestionnaire() {
        if(isMasterDetail) {
            mainConstraintLayout?.transition(R.layout.activity_main)
            rightFrameLayout?.removeAllViews()
        } else {
            goBack()
        }
    }

    private fun ConstraintLayout.transition(layoutId: Int) {
        TransitionManager.beginDelayedTransition(this)
        ConstraintSet().let{
            it.clone(this.context, layoutId)
            it.applyTo(this)
        }
    }


    private fun FrameLayout.replace(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(this.id, fragment)
                .commit()
    }

    private fun goBack() {
        supportFragmentManager.popBackStackImmediate()
    }
}
