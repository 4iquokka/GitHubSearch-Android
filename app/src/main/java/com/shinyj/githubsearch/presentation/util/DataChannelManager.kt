package com.shinyj.githubsearch.presentation.util

import com.shinyj.githubsearch.domain.state.DataState
import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.domain.state.StateMessage
import com.shinyj.githubsearch.domain.state.UIComponentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

abstract class DataChannelManager<ViewState> {

    private var channelScope: CoroutineScope? = null
    private val stateEventManager = StateEventManager()

    val stateMessageQueue = StateMessageQueue()

    val shouldDisplayProgressBar = stateEventManager.shouldDisplayProgressBar

    fun setDataChannel() {
        cancelJobs()
    }

    abstract fun handleNewData(data: ViewState)

    fun launchJob(
        stateEvent: StateEvent,
        jobFunction: Flow<DataState<ViewState>?>
    ) {
        if (isNewStateEventExecutable(stateEvent)) {
            addStateEvent(stateEvent)
            jobFunction.onEach { dState ->
                dState?.let { dataState ->
                    withContext(Main) {
                        dataState.data?.let { data ->
                            handleNewData(data)
                        }
                        dataState.stateMessage?.let { stateMessage ->
                            handleNewStateMessage(stateMessage)
                        }
                        dataState.stateEvent?.let { stateEvent ->
                            removeStateEvent(stateEvent)
                        }
                    }
                }
            }.launchIn(getChannelScope())
        }
    }

    private fun isNewStateEventExecutable(stateEvent: StateEvent): Boolean {
        if (isJobAlreadyActive(stateEvent)) {
            return false
        }
        if (!isMessageQueueEmpty()) {
            if (stateMessageQueue[0].response.uiComponentType == UIComponentType.Toast()) {
                return false
            }
        }
        return true
    }

    fun getChannelScope(): CoroutineScope = channelScope ?: setupNewChannelScope(CoroutineScope(IO))

    private fun setupNewChannelScope(coroutineScope: CoroutineScope): CoroutineScope {
        channelScope = coroutineScope
        return channelScope as CoroutineScope
    }

    fun cancelJobs() {
        if (channelScope != null) {
            if (channelScope?.isActive == true) {
                channelScope?.cancel()
            }
            channelScope = null
        }
        clearActiveStateEvents()
    }

    // StateEvent-Related Methods
    fun addStateEvent(stateEvent: StateEvent) = stateEventManager.addStateEvent(stateEvent)

    fun removeStateEvent(stateEvent: StateEvent) = stateEventManager.removeStateEvent(stateEvent)

    fun clearActiveStateEvents() = stateEventManager.clearActiveStateEvents()

    private fun isStateEventActive(stateEvent: StateEvent) =
        stateEventManager.isStateEventActive(stateEvent)

    fun isJobAlreadyActive(stateEvent: StateEvent) = isStateEventActive(stateEvent)

    // StateMessageQueue-Related Methods
    fun isMessageQueueEmpty() = stateMessageQueue.isEmpty()

    private fun handleNewStateMessage(stateMessage: StateMessage) {
        offerStateMessage(stateMessage)
    }

    private fun offerStateMessage(stateMessage: StateMessage) {
        stateMessageQueue.add(stateMessage)
    }

    fun pollStateMessage() {
        stateMessageQueue.removeAt(0)
    }

    fun clearStateMessages() = stateMessageQueue.clear()


}