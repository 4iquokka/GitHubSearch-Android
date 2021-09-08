package com.shinyj.githubsearch.datastore.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.shinyj.githubsearch.domain.model.Repository

class RepositoryDto (

    @SerializedName("id")
    @Expose
    val id : Long,

    @SerializedName("full_name")
    @Expose
    val name : String,

    @SerializedName("description")
    @Expose
    val description : String?,

    @SerializedName("html_url")
    @Expose
    val url : String?,

    @SerializedName("stargazers_count")
    @Expose
    val numOfStars : Long,

    @SerializedName("forks")
    @Expose
    val numOfForks : Long,

    @SerializedName("watchers")
    @Expose
    val numOfWatchers : Long,

    @SerializedName("created_at")
    @Expose
    val createdAt: String,

    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?

)

fun RepositoryDto.toRepository(): Repository {
    return Repository(
        id = id,
        name = name,
        description = description,
        url = url,
        numOfStars = numOfStars,
        numOfForks = numOfForks,
        numOfWatchers = numOfWatchers,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}