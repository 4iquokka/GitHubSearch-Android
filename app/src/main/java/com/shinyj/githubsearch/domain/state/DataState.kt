package com.shinyj.githubsearch.domain.state

data class DataState<out T>(
    val stateMessage : StateMessage? = null,
    val data : T? = null,
    val stateEvent : StateEvent? = null
) {
    companion object{

        fun <T> data(
            response : Response?,
            data: T? = null,
            stateEvent: StateEvent?
        ) : DataState<T> {
            return DataState(
                stateMessage = response?.let{
                    StateMessage(it)
                },
                data = data,
                stateEvent = stateEvent
            )
        }

        fun <T> error(
            response : Response,
            stateEvent : StateEvent?
        ) : DataState<T>{
            return DataState(
                stateMessage = StateMessage(response),
                data = null,
                stateEvent = stateEvent
            )
        }
    }
}
