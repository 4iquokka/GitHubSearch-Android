package com.shinyj.githubsearch.datastore.network.util

sealed class NetworkResult<out T> {

    data class Success<out T>(val value : T) : NetworkResult<T>()

    data class GenericError(
        val errorCode : Int? = null,
        val errorMessage : Int? = null
    ) : NetworkResult<Nothing>()

    object NetworkError: NetworkResult<Nothing>()

}
