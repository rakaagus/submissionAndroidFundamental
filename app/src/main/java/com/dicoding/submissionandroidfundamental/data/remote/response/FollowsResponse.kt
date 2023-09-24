package com.dicoding.submissionandroidfundamental.data.remote.response

import com.google.gson.annotations.SerializedName

data class FollowsResponse(

	@field:SerializedName("FollowsResponse")
	val followsResponse: List<FollowsResponseItem>
)

data class FollowsResponseItem(

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

)
