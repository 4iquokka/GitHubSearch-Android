package com.shinyj.githubsearch.datastore.network.model

import com.google.gson.annotations.SerializedName
import com.shinyj.githubsearch.domain.model.Repository

class RepositoryDto (

    @SerializedName("id")
    val id : Int,

    @SerializedName("full_name")
    val name : String,

    @SerializedName("description")
    val description : String,

    @SerializedName("stargazers_count")
    val numOfStars : Int,

    @SerializedName("forks")
    val numOfForks : Int,

    @SerializedName("watchers")
    val numOfWatchers : Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String?

)

fun RepositoryDto.toRepository(): Repository {
    return Repository(
        id = id,
        name = name,
        description = description,
        numOfStars = numOfStars,
        numOfForks = numOfForks,
        numOfWatchers = numOfWatchers,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}