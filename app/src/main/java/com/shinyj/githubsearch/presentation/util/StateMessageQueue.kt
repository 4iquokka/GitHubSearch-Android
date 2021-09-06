package com.shinyj.githubsearch.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shinyj.githubsearch.domain.state.MessageType
import com.shinyj.githubsearch.domain.state.Response
import com.shinyj.githubsearch.domain.state.StateMessage
import com.shinyj.githubsearch.domain.state.UIComponentType
import kotlinx.parcelize.IgnoredOnParcel
import java.lang.IndexOutOfBoundsException

/**
 * This ArrayList acts as Queue
 */
class StateMessageQueue : ArrayList<StateMessage>() {

    @IgnoredOnParcel
    private val _stateMessage : MutableLiveData<StateMessage?> = MutableLiveData()

    @IgnoredOnParcel
    val stateMessage : LiveData<StateMessage?>
    get() = _stateMessage

    override fun addAll(elements: Collection<StateMessage>): Boolean {
        for(element in elements){
            add(element)
        }
        return true // We do not care about the return value.
    }

    override fun add(element: StateMessage): Boolean {
        if(this.contains(element)){
            // prevent duplicate messages
            return false
        }
        val transaction = super.add(element)
        if(this.size == 1){
            setStateMessage(element)
        }
        return transaction
    }

    override fun removeAt(index: Int): StateMessage {
        try{
            val transaction = super.removeAt(index)
            if(this.size > 0){
                setStateMessage(this[0])
            }else {
                setStateMessage(null)
            }
            return transaction
        } catch (e : IndexOutOfBoundsException){
            setStateMessage(null)
            e.printStackTrace()
        }
        return StateMessage(
            Response(
                message = "do nothing",
                messageType = MessageType.None(),
                uiComponentType = UIComponentType.None()
            )
        )
    }

    private fun setStateMessage(stateMessage: StateMessage?){
        _stateMessage.value = stateMessage
    }

}