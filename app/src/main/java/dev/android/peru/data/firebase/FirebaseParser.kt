package dev.android.peru.data.firebase

import com.google.gson.Gson
import peru.android.dev.datamodel.Meetup

object FirebaseParser {

    fun toMeetupObject(map: Map<String, Any>): Meetup? {
        val gson = Gson()
        return Gson().fromJson(gson.toJson(map), Meetup::class.java)
    }

}