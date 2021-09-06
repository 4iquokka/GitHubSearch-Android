package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.domain.model.Repository

interface GitHubRepository {

    suspend fun search(
        query : String,
        sort : String?,
        order : String?,
        size: Int,
        page: Int,
    ) : List<Repository>

}