package dev.android.peru.data

object Injection {

    val meetupsRepo by lazy { FirebaseMeetupsRepo() }

    val questionnaireRepo by lazy { FirebaseQuestionnaireRepo() }

    val attendanceRepo by lazy { FirebaseAttendanceRepo() }

}