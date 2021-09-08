package com.shinyj.githubsearch.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.shinyj.githubsearch.R
import com.shinyj.githubsearch.domain.state.MessageType
import com.shinyj.githubsearch.domain.state.Response
import com.shinyj.githubsearch.domain.state.StateMessageCallback
import com.shinyj.githubsearch.domain.state.UIComponentType
import com.shinyj.githubsearch.presentation.util.UIController
import com.shinyj.githubsearch.presentation.util.displayToast
import com.shinyj.githubsearch.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UIController {

    private val TAG = Utils.getTag()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun hideSoftKeyboard() {
        if(currentFocus != null){
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    ) {
        when(response.uiComponentType){
            is UIComponentType.Toast -> {
                response.message?.let{
                    displayToast(
                        message = it,
                        stateMessageCallback = stateMessageCallback
                    )
                }
            }

            is UIComponentType.None -> {
                response.message?.let{ msg ->
                    when(response.messageType){
                        is MessageType.Info,
                        is MessageType.Success -> {
                            Log.i(TAG, msg)
                        }
                        is MessageType.Error -> {
                            Log.e(TAG, msg)
                        }
                        is MessageType.None -> {
                            Log.d(TAG, msg)
                        }
                    }
                }
            }
        }
    }
}