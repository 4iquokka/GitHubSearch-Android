package com.shinyj.githubsearch.datastore.network.util

import com.shinyj.githubsearch.domain.state.*

abstract class NetworkResponseHandler<ViewState, Data>(
    private val response : NetworkResult<Data?>,
    private val stateEvent : StateEvent?
) {
    suspend fun getResult() : DataState<ViewState>? {
        return when(response){
            is NetworkResult.GenericError -> {
                DataState.error(
                    response = Response(
                        message = "${response.errorMessage}(${response.errorCode})",
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
                )
            }

            is NetworkResult.NetworkError -> {
                DataState.error(
                    response = Response(
                        message = NetworkErrors.NETWORK_ERROR,
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Error()
                    ),
                    stateEvent = stateEvent
                )
            }

            is NetworkResult.Success -> {
                if(response.value == null){
                    DataState.error(
                        response = Response(
                            message = NetworkErrors.NETWORK_DATA_NULL,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        ),
                        stateEvent = stateEvent
                    )
                } else {
                    handleOnSuccess(response.value)
                }
            }
        }
    }

    abstract suspend fun handleOnSuccess(resultObj : Data) : DataState<ViewState>?
}