package com.zacky.fundamentalsubmission.remote.retrofit

import com.zacky.fundamentalsubmission.remote.response.GithubResponse
import com.zacky.fundamentalsubmission.model.GithubUser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getGithubUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<GithubUser>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<GithubUser>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<GithubUser>>
}