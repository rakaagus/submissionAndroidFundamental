package com.dicoding.submissionandroidfundamental.data.remote.retrofit

import com.dicoding.submissionandroidfundamental.data.remote.response.DetailUserResponse
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubResponse
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUserByUsername(
        @Query("q") q: String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<GithubUsers>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<GithubUsers>>
}