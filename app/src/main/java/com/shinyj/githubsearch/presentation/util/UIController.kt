package com.shinyj.githubsearch.presentation.util

import com.shinyj.githubsearch.domain.state.Response
import com.shinyj.githubsearch.domain.state.StateMessageCallback

interface UIController {

    fun hideSoftKeyboard()

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

}