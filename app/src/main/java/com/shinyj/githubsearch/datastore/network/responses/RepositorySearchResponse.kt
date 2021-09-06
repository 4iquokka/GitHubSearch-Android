package com.shinyj.githubsearch.datastore.network.responses

import com.google.gson.annotations.SerializedName
import com.shinyj.githubsearch.datastore.network.model.RepositoryDto
import com.shinyj.githubsearch.datastore.network.model.toRepository
import com.shinyj.githubsearch.domain.model.Repository

class RepositorySearchResponse (

    @SerializedName("total_count")
    val totalCount : Long,

    @SerializedName("incomplete_results")
    val isIncompleteResults : Boolean,

    @SerializedName("items")
    val results : List<RepositoryDto>

)

fun RepositorySearchResponse.toList() : List<Repository>{
    val list : MutableList<Repository> = mutableListOf()
    for(dto in results){
        list.add(dto.toRepository())
    }
    return list
}