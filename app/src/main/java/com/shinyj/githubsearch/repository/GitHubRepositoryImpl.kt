package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.datastore.network.GitHubApiService
import com.shinyj.githubsearch.datastore.network.responses.RepositorySearchResponse
import com.shinyj.githubsearch.datastore.network.util.NetworkResponseHandler
import com.shinyj.githubsearch.domain.state.*
import com.shinyj.githubsearch.presentation.search.state.SearchViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubRepositoryImpl
constructor(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {

    override fun search(
        query: String,
        sort: String?,
        order: String?,
        size: Int,
        page: Int,
        stateEvent : StateEvent
    ): Flow<DataState<SearchViewState>?> = flow {
        if(query.isNotEmpty()) {

            val networkResult = safeNetworkCall(IO){
                gitHubApiService.searchRepositories(
                    query = query,
                    sort = sort,
                    order = order,
                    size = size,
                    page = page
                )
            }

            val networkResponse = object : NetworkResponseHandler<SearchViewState, RepositorySearchResponse>(
                response = networkResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleOnSuccess(resultObj: RepositorySearchResponse): DataState<SearchViewState>? {
                    return DataState.data(
                        response = Response(
                            message = "",
                            uiComponentType = UIComponentType.None(),
                            messageType = MessageType.Success()
                        ),
                        data = SearchViewState(

                        ),
                        stateEvent = stateEvent
                    )
                }

            }.getResult()

            emit(networkResponse)
        }
    }

}