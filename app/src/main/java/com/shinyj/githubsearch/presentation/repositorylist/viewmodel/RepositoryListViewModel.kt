package com.shinyj.githubsearch.presentation.repositorylist.viewmodel

import com.shinyj.githubsearch.domain.state.DataState
import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListStateEvent.CreateStateMessageEvent
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListStateEvent.SearchRepositoriesEvent
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListViewState
import com.shinyj.githubsearch.presentation.util.BaseViewModel
import com.shinyj.githubsearch.presentation.util.UIConstants.PAGINATION_PAGE_LIMIT
import com.shinyj.githubsearch.repository.GitHubRepository
import com.shinyj.githubsearch.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RepositoryListViewModel
@Inject
constructor(
    private val gitHubRepository: GitHubRepository
) : BaseViewModel<RepositoryListViewState>(){

    val TAG = Utils.getTag()

    override fun handleNewData(data: RepositoryListViewState) {
        data.let{ viewState ->
            viewState.repositoryList?.let{ list ->
                handleIncomingRepositoryList(list)
                setIsQueryExhausted(true)
            }
            viewState.isLastPage.let{ isLastPage ->
                setIsLastPage(isLastPage)
            }
            viewState.totalCount.let{ count ->
                setTotalCount(count)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job : Flow<DataState<RepositoryListViewState>?> = when(stateEvent){

            is SearchRepositoriesEvent -> {
                gitHubRepository.search(
                    query = getQuery(),
                    sort = null,
                    order = null,
                    page = getPage(),
                    size = PAGINATION_PAGE_LIMIT,
                    stateEvent = stateEvent
                )
            }

            is CreateStateMessageEvent -> {
                emitStateMessageEvent(
                    stateMessage = stateEvent.stateMessage,
                    stateEvent = stateEvent
                )
            }

            else -> {
                emitInvalideStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState() = RepositoryListViewState()

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}