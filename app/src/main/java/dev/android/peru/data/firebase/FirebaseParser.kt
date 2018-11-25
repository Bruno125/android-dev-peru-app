package dev.android.peru.data.firebase

import com.google.gson.Gson
import peru.android.dev.datamodel.Attendance
import peru.android.dev.datamodel.Meetup

object FirebaseParser {

    fun toMeetupObject(map: Map<String, Any>): Meetup? {
        val gson = Gson()
        return Gson().fromJson(gson.toJson(map), Meetup::class.java)
    }

    fun toAttendance(data: List<Map<String, Any>>): List<Attendance> {
        val gson = Gson()
        return data.mapNotNull { gson.fromJson(gson.toJson(it), Attendance::class.java) }
    }

}