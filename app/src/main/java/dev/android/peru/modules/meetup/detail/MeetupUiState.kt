package dev.android.peru.modules.meetup.detail

import peru.android.dev.datamodel.Meetup

sealed class MeetupUiState {

    object Loading: MeetupUiState()

    class Detail(val meetup: Meetup): MeetupUiState()

}