package dev.android.peru.data.repositories

import dev.android.peru.data.source.firebase.FirebaseParser
import dev.android.peru.data.source.firebase.FirebaseRepo
import peru.android.dev.datamodel.Attendance
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface AttendanceRepo {
    suspend fun getAttendance(meetupId: String): List<Attendance>
}
