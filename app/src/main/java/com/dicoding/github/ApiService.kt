package com.dicoding.github

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<GithubResponse>
    @GET("users")
    fun getListUser(): Call<List<UsersItem>>
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String
    ): Call<DetailUserResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String
    ): Call<List<UsersItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String
    ): Call<List<UsersItem>>
}