package com.shinyj.githubsearch.presentation.util

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import com.shinyj.githubsearch.domain.state.StateMessageCallback

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (visibility == View.VISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

fun View.rotate() {
    startAnimation(
        RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        ).also{
            it.duration = 3000L
            it.repeatMode = Animation.RESTART
            it.repeatCount = Animation.INFINITE
        }
    )
}

fun Activity.displayToast(
    message: String,
    stateMessageCallback: StateMessageCallback
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    stateMessageCallback.removeMessageFromQueue()
}

fun Activity.displayToast(
    message: String,
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
