package com.zacky.fundamentalsubmission.remote.response

import com.google.gson.annotations.SerializedName
import com.zacky.fundamentalsubmission.model.FavoriteUser
import com.zacky.fundamentalsubmission.model.GithubUser

data class GithubResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    val items: List<GithubUser>
)