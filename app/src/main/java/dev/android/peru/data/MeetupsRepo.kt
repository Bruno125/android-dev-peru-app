package dev.android.peru.data

import dev.android.peru.data.firebase.FirebaseParser
import dev.android.peru.data.firebase.FirebaseRepo
import peru.android.dev.datamodel.Meetup
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface MeetupsRepo {

    suspend fun getMeetups(): List<Meetup>

}

class FirebaseMeetupsRepo(): MeetupsRepo, FirebaseRepo {

    override suspend fun getMeetups(): List<Meetup> {
        return suspendCoroutine { continuation ->
            readCollection(path = "meetups", onSuccess = { result ->
                val meetups = result?.documents
                        ?.mapNotNull { FirebaseParser.toMeetupObject(
                                meetupId = it?.id ?: "",
                                data = it?.data ?: emptyMap())
                        }
                        ?: emptyList()
                continuation.resume(meetups)
            })
        }
    }

}
