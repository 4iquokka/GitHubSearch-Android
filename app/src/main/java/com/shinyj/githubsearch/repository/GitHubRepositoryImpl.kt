package com.shinyj.githubsearch.repository

import com.shinyj.githubsearch.datastore.network.GitHubApiService
import com.shinyj.githubsearch.datastore.network.responses.toList
import com.shinyj.githubsearch.domain.model.Repository

class GitHubRepositoryImpl
constructor(
    private val gitHubApiService: GitHubApiService
) : GitHubRepository {

    override suspend fun search(
        query: String,
        sort: String?,
        order: String?,
        size: Int,
        page: Int
    ): List<Repository> = gitHubApiService.searchRepositories(
        query = query,
        sort = sort,
        order = order,
        size = size,
        page = page
    ).toList()

}