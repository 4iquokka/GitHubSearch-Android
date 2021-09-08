package com.shinyj.githubsearch.presentation.repositorylist.viewmodel

import android.os.Parcelable
import com.shinyj.githubsearch.domain.model.Repository

fun RepositoryListViewModel.getRepositoryList() : List<Repository>? {
    return getViewState().repositoryList
}

fun RepositoryListViewModel.getQuery() : String {
    return getViewState().query
}

fun RepositoryListViewModel.getPage(): Int {
    return getViewState().page
}

fun RepositoryListViewModel.getTotalCounts() : Long {
    return getViewState().totalCount
}

fun RepositoryListViewModel.getIsLastPage() : Boolean {
    return getViewState().isLastPage
}

fun RepositoryListViewModel.getIsQueryExhausted() : Boolean {
    return getViewState().isQueryExhausted ?: true
}

fun RepositoryListViewModel.getLayoutManagerState() : Parcelable? {
    return getViewState().layoutManagerState
}
