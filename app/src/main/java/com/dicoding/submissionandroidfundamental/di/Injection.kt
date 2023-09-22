package com.dicoding.submissionandroidfundamental.di

import android.content.Context
import com.dicoding.submissionandroidfundamental.data.UserGithubRepository
import com.dicoding.submissionandroidfundamental.data.local.room.UserGithubDatabase
import com.dicoding.submissionandroidfundamental.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRespository(context: Context): UserGithubRepository{
        val apiService = ApiConfig.getApiService()
        val database = UserGithubDatabase.getInstance(context)
        val dao = database.userGithubDao()
        return UserGithubRepository.getInstance(apiService, dao)
    }
}