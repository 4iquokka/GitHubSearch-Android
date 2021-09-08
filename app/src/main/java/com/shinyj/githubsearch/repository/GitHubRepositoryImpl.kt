package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.datastore.network.GitHubApiService
import com.shinyj.githubsearch.datastore.network.responses.RepositorySearchResponse
import com.shinyj.githubsearch.datastore.network.responses.toList
import com.shinyj.githubsearch.datastore.network.util.NetworkResponseHandler
import com.shinyj.githubsearch.domain.state.*
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListViewState
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
    ): Flow<DataState<RepositoryListViewState>?> = flow {
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

            val networkResponse = object : NetworkResponseHandler<RepositoryListViewState, RepositorySearchResponse>(
                response = networkResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleOnSuccess(resultObj: RepositorySearchResponse): DataState<RepositoryListViewState>? {
                    return DataState.data(
                        response = Response(
                            message = "Successfully fetched",
                            uiComponentType = UIComponentType.None(),
                            messageType = MessageType.Success()
                        ),
                        data = RepositoryListViewState(
                            repositoryList = resultObj.toList(),
                            isLastPage = resultObj.isLastPage,
                            totalCount = resultObj.totalCount
                        ),
                        stateEvent = stateEvent
                    )
                }

            }.getResult()

            emit(networkResponse)
        }
    }

}