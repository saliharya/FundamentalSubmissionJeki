package com.zacky.fundamentalsubmission.remote.response

import com.google.gson.annotations.SerializedName
import com.zacky.fundamentalsubmission.model.ItemsItem

data class GithubResponse(

    @field:SerializedName("total_count") val totalCount: Int,

    @field:SerializedName("incomplete_results") val incompleteResults: Boolean,

    @field:SerializedName("items") val items: List<ItemsItem>
)