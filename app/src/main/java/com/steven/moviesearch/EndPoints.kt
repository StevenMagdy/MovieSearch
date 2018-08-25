package com.steven.moviesearch

import com.steven.moviesearch.models.ResultItem
import com.steven.moviesearch.models.SearchResult

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EndPoints {

	@GET("search/multi")
	fun getSearchResult(
			@Query("api_key") key: String,
			@Query("query") SearchQuery: String
	): Call<SearchResult>

	@GET("movie/{id}")
	fun getMovieDetails(
			@Path("id") id: Int,
			@Query("api_key") key: String): Call<ResultItem>

	@GET("tv/{id}")
	fun getTVDetails(
			@Path("id") id: Int,
			@Query("api_key") key: String): Call<ResultItem>
}
