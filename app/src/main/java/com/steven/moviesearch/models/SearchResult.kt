package com.steven.moviesearch.models

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("page") var pageNumber: Int?,
						@SerializedName("total_results") var totalResultsNumber: Int?,
						@SerializedName("total_pages") var totalPagesNumber: Int?,
						@SerializedName("results") var results: List<ResultItem>?)
