package dev.android.peru.modules.search

import peru.android.dev.datamodel.Attendance

sealed class SearchUserUiState {

    object PleaseSearch: SearchUserUiState()

    object Loading: SearchUserUiState()

    class DisplayResults(val users: List<Attendance>): SearchUserUiState()

    object NoResults: SearchUserUiState()
}