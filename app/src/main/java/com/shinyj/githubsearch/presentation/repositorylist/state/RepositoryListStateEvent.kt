package com.shinyj.githubsearch.presentation.repositorylist.state

import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.domain.state.StateMessage

sealed class RepositoryListStateEvent : StateEvent {

    class SearchRepositoriesEvent(
        val clearLayoutManagerState: Boolean = true
    ) : RepositoryListStateEvent() {

        override fun eventName(): String = "SearchRepositoriesEvent"

        override fun shouldDisplayProgressBar(): Boolean = true
    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : RepositoryListStateEvent() {

        override fun eventName(): String = "CreateStateMessageEvent"

        override fun shouldDisplayProgressBar(): Boolean = false
    }
}
