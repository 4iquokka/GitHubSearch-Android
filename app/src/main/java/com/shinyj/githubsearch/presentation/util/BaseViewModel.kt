package com.shinyj.githubsearch.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shinyj.githubsearch.domain.state.*
import com.shinyj.githubsearch.domain.util.GenericErrors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseViewModel<ViewState> : ViewModel() {

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    val viewState: LiveData<ViewState>
        get() = _viewState

    val dataChannelManager: DataChannelManager<ViewState> =
        object : DataChannelManager<ViewState>() {
            override fun handleNewData(data: ViewState) {
                this@BaseViewModel.handleNewData(data)
            }
        }

    val stateMessage: LiveData<StateMessage?>
        get() = dataChannelManager.stateMessageQueue.stateMessage

    fun setDataChannel() = dataChannelManager.setDataChannel()

    fun launchJob(
        stateEvent : StateEvent,
        jobFunction : Flow<DataState<ViewState>?>
    ) = dataChannelManager.launchJob(stateEvent, jobFunction)

    fun getViewState() : ViewState {
        return viewState.value ?: initNewViewState()
    }

    fun setViewState(viewState: ViewState){
        _viewState.value = viewState
    }

    fun emitStateMessageEvent(
        stateMessage : StateMessage,
        stateEvent: StateEvent
    ) = flow {
        emit(
            DataState.error<ViewState>(
                response = stateMessage.response,
                stateEvent = stateEvent
            )
        )
    }

    fun emitInvalideStateEvent(
        stateEvent: StateEvent
    ) = flow {
        emit(
            DataState.error<ViewState>(
                response = Response(
                    message = GenericErrors.INVALID_STATE_EVENT,
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.Error()
                ),
                stateEvent = stateEvent
            )
        )
    }

    fun pollStateMessage() = dataChannelManager.pollStateMessage()

    fun clearActiveStateEvent() = dataChannelManager.clearActiveStateEvents()

    fun clearStateMessages() = dataChannelManager.clearStateMessages()

    fun cancelActiveJobs() = dataChannelManager.cancelJobs()

    abstract fun handleNewData(data: ViewState)

    abstract fun setStateEvent(stateEvent : StateEvent)

    abstract fun initNewViewState() : ViewState

}