package dev.android.peru.data.source.firebase

import com.google.gson.Gson
import peru.android.dev.datamodel.Attendance
import peru.android.dev.datamodel.Meetup

object FirebaseParser {

    val gson by lazy { Gson() }

    fun toMeetupObject(meetupId: String, data: Map<String, Any>): Meetup? {
        return Gson().fromJson(gson.toJson(data), Meetup::class.java).copy(id = meetupId)
    }

    fun toAttendance(data: List<Map<String, Any>>): List<Attendance> {
        return data.mapNotNull { gson.fromJson(gson.toJson(it), Attendance::class.java) }
    }

}