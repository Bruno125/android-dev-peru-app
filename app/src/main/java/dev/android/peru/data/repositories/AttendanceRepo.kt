package dev.android.peru.data.repositories

import peru.android.dev.datamodel.Attendance

interface AttendanceRepo {
    suspend fun getAttendance(meetupId: String): List<Attendance>

    suspend fun updateAttendance(meetupId: String, attendance: Attendance): Boolean
}
