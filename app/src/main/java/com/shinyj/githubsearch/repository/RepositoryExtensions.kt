package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.datastore.network.util.NetworkConstants.NETWORK_TIMEOUT
import com.shinyj.githubsearch.datastore.network.util.NetworkErrors.ERROR_CODE_NETWORK_TIMEOUT
import com.shinyj.githubsearch.datastore.network.util.NetworkErrors.ERROR_NETWORK_TIMEOUT
import com.shinyj.githubsearch.datastore.network.util.NetworkErrors.ERROR_NETWORK_UNKNOWN
import com.shinyj.githubsearch.datastore.network.util.NetworkResult
import com.shinyj.githubsearch.domain.util.GenericErrors.ERROR_UNKNOWN
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException

/**
 * Reference: https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912
 */
suspend fun <T> safeNetworkCall(
    dispatcher: CoroutineDispatcher,
    networkCall: suspend () -> T?
): NetworkResult<T?> {
    return withContext(dispatcher){
        try {
            withTimeout(NETWORK_TIMEOUT){
                NetworkResult.Success(networkCall.invoke())
            }
        } catch (e : Throwable){
            e.printStackTrace()
            when(e){
                is TimeoutCancellationException -> {
                    NetworkResult.GenericError(ERROR_CODE_NETWORK_TIMEOUT, ERROR_NETWORK_TIMEOUT)
                }
                is IOException -> {
                    NetworkResult.NetworkError
                }
                is HttpException -> {
                    val errorResponse = e.response()?.errorBody()?.toString() ?: ERROR_UNKNOWN
                    NetworkResult.GenericError(e.code(), errorResponse)
                }
                else -> {
                    NetworkResult.GenericError(null, ERROR_NETWORK_UNKNOWN)
                }
            }
        }
    }
}