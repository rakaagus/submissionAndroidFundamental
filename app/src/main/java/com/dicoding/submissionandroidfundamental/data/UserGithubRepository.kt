package com.dicoding.submissionandroidfundamental.data


import androidx.lifecycle.LiveData
import com.dicoding.submissionandroidfundamental.data.local.entity.UserGithubEntity
import com.dicoding.submissionandroidfundamental.data.local.room.UserGithubDao
import com.dicoding.submissionandroidfundamental.data.remote.retrofit.ApiService

class UserGithubRepository private constructor(
    private val userGithubDao: UserGithubDao
){

    suspend fun deleteFavUser(username: String){
        userGithubDao.deleteUserGithub(username)
    }

    suspend fun insertUser(githubEntity: UserGithubEntity){
        userGithubDao.insertUserGithub(githubEntity)
    }

    fun isUserFav(username: String): LiveData<Boolean> = userGithubDao.isUserFav(username)


    fun getAllUserFav(): LiveData<List<UserGithubEntity>> = userGithubDao.getAllUserFav()

    companion object{
        private var INSTANCE: UserGithubRepository? = null
        fun getInstance(
            userGithubDao: UserGithubDao
        ): UserGithubRepository =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: UserGithubRepository(userGithubDao)
            }.also { INSTANCE = it }
    }
}