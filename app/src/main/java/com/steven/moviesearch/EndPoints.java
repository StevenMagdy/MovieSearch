package com.steven.moviesearch;

import com.steven.moviesearch.models.ResultItem;
import com.steven.moviesearch.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPoints {

	@GET("search/movie")
	Call<SearchResult> getSearchResult(
			@Query("api_key") String key,
			@Query("query") String SearchQuery
	);

	@GET("movie/{id}")
	Call<ResultItem> getMovieDetails(
			@Path("id") int id,
			@Query("api_key") String key);
}
