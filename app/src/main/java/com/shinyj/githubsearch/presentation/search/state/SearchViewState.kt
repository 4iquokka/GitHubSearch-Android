package com.shinyj.githubsearch.presentation.search.state

import android.os.Parcelable
import com.shinyj.githubsearch.domain.state.ViewState
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchViewState(
    var someString: String? = null
) : Parcelable, ViewState {

}