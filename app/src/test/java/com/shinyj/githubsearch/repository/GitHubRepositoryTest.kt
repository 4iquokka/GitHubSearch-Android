package com.shinyj.githubsearch.repository

import com.google.gson.GsonBuilder
import com.shinyj.githubsearch.datastore.network.GitHubApiService
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl : HttpUrl
    private lateinit var gitHubRepository : GitHubRepository
    private lateinit var gitHubApiService: GitHubApiService

    @BeforeEach
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/search/repositories")
        gitHubApiService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
            .build()
            .create(GitHubApiService::class.java)

        gitHubRepository = GitHubRepositoryImpl(gitHubApiService)
    }

}