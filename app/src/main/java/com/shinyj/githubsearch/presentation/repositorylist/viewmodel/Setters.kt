package com.shinyj.githubsearch.presentation.repositorylist.viewmodel

import android.os.Parcelable
import com.shinyj.githubsearch.domain.model.Repository

fun RepositoryListViewModel.setRepositoryList(list: List<Repository>){
    val update = getViewState()
    update.repositoryList = list
    setViewState(update)
}

fun RepositoryListViewModel.setPage(page: Int){
    val update = getViewState()
    update.page = page
    setViewState(update)
}

fun RepositoryListViewModel.setQuery(query: String){
    val update = getViewState()
    update.query = query
    setViewState(update)
}

fun RepositoryListViewModel.setTotalCount(count: Long){
    val update = getViewState()
    update.totalCount = count
    setViewState(update)
}

fun RepositoryListViewModel.setIsLastPage(isLastPage : Boolean){
    val update = getViewState()
    update.isLastPage = isLastPage
    setViewState(update)
}

fun RepositoryListViewModel.setIsQueryExhausted(isQueryExhausted : Boolean){
    val update = getViewState()
    update.isQueryExhausted = isQueryExhausted
    setViewState(update)
}

fun RepositoryListViewModel.setLayoutManagerState(layoutManagerState : Parcelable?){
    val update = getViewState()
    update.layoutManagerState = layoutManagerState
    setViewState(update)
}

