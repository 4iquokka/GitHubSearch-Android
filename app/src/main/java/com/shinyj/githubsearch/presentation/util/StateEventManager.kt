package com.shinyj.githubsearch.presentation.util

import com.shinyj.githubsearch.domain.state.StateEvent

/**
 * This keeps track of active StateEvents in DataChannelManager.
 */
class StateEventManager {

    private val activeStateEvents : HashMap<String, StateEvent> = HashMap()

    fun getActiveStateEventNames(): MutableSet<String>{
        return activeStateEvents.keys
    }

    fun addStateEvent(stateEvent: StateEvent){
        activeStateEvents[stateEvent.eventName()] = stateEvent
    }

    fun removeStateEvent(stateEvent: StateEvent){
        activeStateEvents.remove(stateEvent.eventName())
    }

    fun clearActiveStateEvents(){
        activeStateEvents.clear()
    }

    fun isStateEventActive(stateEvent: StateEvent) : Boolean =
        activeStateEvents.containsKey(stateEvent.eventName())
}