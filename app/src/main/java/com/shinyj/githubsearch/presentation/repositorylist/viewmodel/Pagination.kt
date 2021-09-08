package com.shinyj.githubsearch.presentation.repositorylist.viewmodel

import android.util.Log
import com.shinyj.githubsearch.domain.model.Repository
import com.shinyj.githubsearch.presentation.repositorylist.state.RepositoryListStateEvent.*

fun RepositoryListViewModel.resetPage() {
    setPage(1)
    emptyList()
}

fun RepositoryListViewModel.loadFirstPage() {
    if (!isJobAlreadyActive(SearchRepositoriesEvent())) {
        Log.d(TAG, "Attempting to load first page...")
        setIsQueryExhausted(false)
        resetPage()
        setStateEvent(SearchRepositoriesEvent())
    }
}

fun RepositoryListViewModel.loadNextPage() {
    if (!isJobAlreadyActive(SearchRepositoriesEvent())
        && getIsQueryExhausted() && !getIsLastPage()
    ) {
        Log.d(TAG, "Attempting to load next page...")
        setIsQueryExhausted(false)
        setLayoutManagerState(null)
        incrementPageNumber()
        setStateEvent(SearchRepositoriesEvent())
    }
}

fun RepositoryListViewModel.emptyList() {
    val update = getViewState()
    update.repositoryList = listOf()
    setViewState(update)
}

private fun RepositoryListViewModel.incrementPageNumber() {
    val update = getViewState()
    val page = update.copy().page
    update.page = page.plus(1)
    setViewState(update)
}

fun RepositoryListViewModel.handleIncomingRepositoryList(list: List<Repository>){
    val update = getViewState()
    val existingList = update.copy().repositoryList ?: listOf()
    val mutableSet = existingList.toMutableSet()
    mutableSet.addAll(list)
    update.repositoryList = mutableSet.toList()
    setViewState(update)
}