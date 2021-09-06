package com.shinyj.githubsearch.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shinyj.githubsearch.datastore.network.GitHubApiService
import com.shinyj.githubsearch.datastore.network.util.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gsonBuilder: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
    }

    @Singleton
    @Provides
    fun provideGitHubApiService(retrofitBuilder : Retrofit.Builder) : GitHubApiService {
        return retrofitBuilder
            .build()
            .create(GitHubApiService::class.java)
    }

}