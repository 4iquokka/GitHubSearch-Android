package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.domain.state.DataState
import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListViewState
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {

    fun search(
        query : String,
        sort : String?,
        order : String?,
        size: Int,
        page: Int,
        stateEvent : StateEvent
    ) : Flow<DataState<RepositoryListViewState>?>

}