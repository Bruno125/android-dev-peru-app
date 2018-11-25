package dev.android.peru.data.repositories

import peru.android.dev.datamodel.Meetup

interface MeetupsRepo {
    suspend fun getMeetups(): List<Meetup>
}
