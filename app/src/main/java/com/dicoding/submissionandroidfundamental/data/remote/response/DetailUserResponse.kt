package com.dicoding.submissionandroidfundamental.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("twitter_username")
	val twitterUsername: Any,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("company")
	val company: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: Any,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String,

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,
)
