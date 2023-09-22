package com.dicoding.submissionandroidfundamental.data.remote.response

import com.google.gson.annotations.SerializedName

data class GithubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<GithubUsers>
)

data class GithubUsers(

	@field:SerializedName("login")
	var login: String? = null,

	@field:SerializedName("avatar_url")
	var avatarUrl: String? = null,

)
