package com.steven.moviesearch.models

import com.google.gson.annotations.SerializedName

data class ResultItem(
		@SerializedName("media_type") var mediaType: String?,
		@SerializedName("vote_count") var voteCount: Int?,
		@SerializedName("id") var id: Int?,
		@SerializedName("vote_average") var averageVote: Float?,
		@SerializedName(value = "title", alternate = ["name"]) var title: String?,
		@SerializedName("poster_path") var posterPath: String?,
		@SerializedName("original_language") var originalLanguage: String?,
		@SerializedName("original_title") var originalTitle: String?,
		@SerializedName("backdrop_path") var backdropPath: Any?,
		@SerializedName("genres") var genres: List<Genre>?,
		@SerializedName("adult") var isAdult: Boolean?,
		@SerializedName("overview") var overview: String?,
		@SerializedName("runtime") var runtime: Int?,
		@SerializedName(value = "release_date", alternate = ["first_air_date"]) var releaseDate: String?
) {
	val year: String?
		get() {
			if (!releaseDate.isNullOrEmpty() && mediaType == "movie")
				return releaseDate!!.substring(0, 4)
			else
				return null
		}
}
