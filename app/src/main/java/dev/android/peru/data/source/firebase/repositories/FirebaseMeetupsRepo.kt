package dev.android.peru.data.source.firebase.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dev.android.peru.data.repositories.MeetupsRepo
import dev.android.peru.data.source.firebase.FirebaseParser
import dev.android.peru.data.source.firebase.FirebaseRepo
import peru.android.dev.datamodel.Meetup
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseMeetupsRepo(): MeetupsRepo, FirebaseRepo {

    override suspend fun getMeetups(): List<Meetup> {
        return suspendCoroutine { continuation ->
            readCollection(path = "meetups",
                    onSuccess =  { continuation.resume(it.asMeetupList()) },
                    onCanceled = { continuation.resume(emptyList()) },
                    onFailure =  { continuation.resume(emptyList()) })
        }
    }

    override suspend fun getMeetup(meetupId: String): Meetup? {
        return suspendCoroutine { continuation ->
            readDocument("meetups/$meetupId",
                    onSuccess =  { continuation.resume(it.asMeetupObject(meetupId)) },
                    onCanceled = { continuation.resume(value = null) },
                    onFailure =  { continuation.resume(value = null) })
        }
    }

    private fun QuerySnapshot?.asMeetupList() = this
            ?.documents
            ?.mapNotNull { FirebaseParser.toMeetupObject(
                    meetupId = it?.id ?: "",
                    data = it?.data ?: emptyMap())
            }
            ?: emptyList()

    private fun DocumentSnapshot?.asMeetupObject(id: String) =
            FirebaseParser.toMeetupObject(id, data = this?.data ?: emptyMap())
}
