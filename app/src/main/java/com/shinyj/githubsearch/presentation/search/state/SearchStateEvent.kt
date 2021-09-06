package com.shinyj.githubsearch.presentation.search.state

import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.domain.state.StateMessage

sealed class SearchStateEvent : StateEvent {

    class SearchRepositoriesEvent(
        val query : String,
        val sort: String,
        val order : String,
        val page: Int,
        val size : Int,
    ) : SearchStateEvent() {
        override fun eventName(): String = "SearchRepositoriesEvent"
    }

    class CreateStateMessageEvent(
        val stateMessage: StateMessage
    ) : SearchStateEvent() {
        override fun eventName(): String = "CreateStateMessageEvent"
    }
}
