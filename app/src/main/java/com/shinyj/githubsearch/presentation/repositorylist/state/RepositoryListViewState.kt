package com.shinyj.githubsearch.presentation.repositorylist.state

import android.os.Parcelable
import com.shinyj.githubsearch.domain.model.Repository
import com.shinyj.githubsearch.domain.state.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryListViewState(
    var repositoryList: List<Repository>? = null,
    var query: String = "",
    var page: Int = 1,
    var totalCount: Long = 0,
    var isLastPage : Boolean = false,
    var isQueryExhausted: Boolean? = null,
    var layoutManagerState: Parcelable? = null
) : Parcelable, ViewState {

}