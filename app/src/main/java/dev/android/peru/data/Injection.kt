package dev.android.peru.data

import dev.android.peru.data.source.firebase.repositories.*

object Injection {

    val meetupsRepo by lazy { FirebaseMeetupsRepo() }

    val questionnaireRepo by lazy { FirebaseQuestionnaireRepo() }

    val attendanceRepo by lazy { FirebaseAttendanceRepo() }

}