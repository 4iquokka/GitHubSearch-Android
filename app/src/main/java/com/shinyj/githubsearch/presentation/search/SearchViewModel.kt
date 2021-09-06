package com.shinyj.githubsearch.presentation.search

import com.shinyj.githubsearch.domain.state.DataState
import com.shinyj.githubsearch.domain.state.StateEvent
import com.shinyj.githubsearch.presentation.search.state.SearchStateEvent.CreateStateMessageEvent
import com.shinyj.githubsearch.presentation.search.state.SearchStateEvent.SearchRepositoriesEvent
import com.shinyj.githubsearch.presentation.search.state.SearchViewState
import com.shinyj.githubsearch.presentation.util.BaseViewModel
import com.shinyj.githubsearch.presentation.util.UIConstants.PAGINATION_PAGE_LIMIT
import com.shinyj.githubsearch.repository.GitHubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val gitHubRepository: GitHubRepository
) : BaseViewModel<SearchViewState>(){

    override fun handleNewData(data: SearchViewState) {
        data.let{ viewState ->

        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job : Flow<DataState<SearchViewState>?> = when(stateEvent){

            is SearchRepositoriesEvent -> {
                gitHubRepository.search(
                    query = stateEvent.query,
                    sort = stateEvent.sort,
                    order = stateEvent.order,
                    page = 1,
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

    override fun initNewViewState() = SearchViewState()

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}