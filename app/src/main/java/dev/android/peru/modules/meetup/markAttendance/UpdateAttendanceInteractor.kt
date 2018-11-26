package dev.android.peru.modules.meetup.markAttendance

import dev.android.peru.data.Injection
import dev.android.peru.data.repositories.AttendanceRepo
import peru.android.dev.datamodel.Attendance

/**
 * Will change the attendance status and return the new value
 */
class UpdateAttendanceInteractor(val repo: AttendanceRepo = Injection.attendanceRepo){

    suspend fun execute(meetupId: String, attendance: Attendance): Attendance {
        val updated = attendance.copy(didShowUp = !attendance.didShowUp)
        val success = repo.updateAttendance(meetupId, updated)
        return if(success) updated else attendance
    }

}