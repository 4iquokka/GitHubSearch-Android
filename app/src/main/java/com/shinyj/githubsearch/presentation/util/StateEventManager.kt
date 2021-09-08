package com.shinyj.githubsearch.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shinyj.githubsearch.domain.state.StateEvent

/**
 * This keeps track of active StateEvents in DataChannelManager.
 */
class StateEventManager {

    private val activeStateEvents : HashMap<String, StateEvent> = HashMap()

    private val _shouldDisplayProgressBar : MutableLiveData<Boolean> = MutableLiveData()

    val shouldDisplayProgressBar : LiveData<Boolean>
    get() = _shouldDisplayProgressBar


    fun getActiveStateEventNames(): MutableSet<String>{
        return activeStateEvents.keys
    }

    fun addStateEvent(stateEvent: StateEvent){
        activeStateEvents[stateEvent.eventName()] = stateEvent
        syncNumActiveStateEvents()
    }

    fun removeStateEvent(stateEvent: StateEvent){
        activeStateEvents.remove(stateEvent.eventName())
        syncNumActiveStateEvents()
    }

    fun clearActiveStateEvents(){
        activeStateEvents.clear()
        syncNumActiveStateEvents()
    }

    fun isStateEventActive(stateEvent: StateEvent) : Boolean =
        activeStateEvents.containsKey(stateEvent.eventName())

    private fun syncNumActiveStateEvents(){
        var shouldDisplayProgressBar = false
        for(stateEvent in activeStateEvents.values){
            if(stateEvent.shouldDisplayProgressBar()){
                shouldDisplayProgressBar = true
            }
        }
        _shouldDisplayProgressBar.value = shouldDisplayProgressBar
    }
}