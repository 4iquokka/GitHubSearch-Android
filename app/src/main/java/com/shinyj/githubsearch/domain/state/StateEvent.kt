package com.shinyj.githubsearch.domain.state

interface StateEvent {
    fun eventName() : String
}