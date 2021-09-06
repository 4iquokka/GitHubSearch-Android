package com.shinyj.githubsearch.domain.state


data class StateMessage(
    val response: Response
)

data class Response(
    val message: String?,
    val messageType : MessageType,
    val uiComponentType: UIComponentType
)

sealed class UIComponentType {
    class Toast : UIComponentType()
    class None : UIComponentType()
}

sealed class MessageType {
    class Success : MessageType()
    class Error : MessageType()
    class Info : MessageType()
    class None : MessageType()
}

interface StateMessageCallback {
    fun removeMessageFromQueue()
}