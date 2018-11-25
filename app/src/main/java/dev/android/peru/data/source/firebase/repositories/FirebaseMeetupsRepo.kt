package dev.android.peru.data.source.firebase.repositories

import dev.android.peru.data.repositories.MeetupsRepo
import dev.android.peru.data.source.firebase.FirebaseParser
import dev.android.peru.data.source.firebase.FirebaseRepo
import peru.android.dev.datamodel.Meetup
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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
