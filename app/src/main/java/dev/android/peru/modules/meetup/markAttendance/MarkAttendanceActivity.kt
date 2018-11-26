package dev.android.peru.modules.meetup.markAttendance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.android.peru.R
import peru.android.dev.androidutils.start

class MarkAttendanceActivity : AppCompatActivity() {

    companion object {
        const val PARAM_MEETUP_ID = "PARAM_MEETUP_ID"

        fun start(activity: AppCompatActivity, meetupId: String) {
            activity.start<MarkAttendanceActivity>(Bundle().apply {
                putString(PARAM_MEETUP_ID, meetupId)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_attendance)

        val meetupId = intent?.extras?.getString(PARAM_MEETUP_ID) ?: ""

        supportFragmentManager.beginTransaction()
                .add(R.id.markAttendanceActivityLayout, MarkAttendanceFragment.newInstance(meetupId))
                .commit()
    }

}
