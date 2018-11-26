package dev.android.peru.data.source.mock.repositories

import dev.android.peru.data.repositories.AttendanceRepo
import peru.android.dev.datamodel.Attendance

class MockAttendanceRepo: AttendanceRepo {

    override suspend fun getAttendance(meetupId: String): List<Attendance> {
        return listOf(
                Attendance(id = "1234567", name = "Bruno Aybar", hasVoted = false, didShowUp = false),
                Attendance(id = "7654321", name = "Eduardo Media", hasVoted = false, didShowUp = false),
                Attendance(id = "1346791", name = "Pablo Johnson", hasVoted = true, didShowUp = false),
                Attendance(id = "5345345", name = "Jose Flavio", hasVoted = false , didShowUp = true),
                Attendance(id = "7893214", name = "Jonathan Nolasco", hasVoted = false, didShowUp = false),
                Attendance(id = "1321089", name = "Jonathan Choy", hasVoted = true, didShowUp = false),
                Attendance(id = "6781345", name = "Daniel Anaya", hasVoted = false, didShowUp = true)
        )
    }

    override suspend fun updateAttendance(meetupId: String, attendance: Attendance): Boolean {
        return true
    }
}
