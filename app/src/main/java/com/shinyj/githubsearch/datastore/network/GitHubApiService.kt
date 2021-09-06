package com.shinyj.githubsearch.datastore.network

import com.shinyj.githubsearch.datastore.network.responses.RepositorySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApiService {

    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String?,
        @Query("order") order: String?,
        @Query("per_page") size : Int,
        @Query("page") page: Int
    ) : RepositorySearchResponse

}