package com.steven.moviesearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {

	@SerializedName("page")
	private int pageNumber;

	@SerializedName("total_results")
	private int totalResultsNumber;

	@SerializedName("total_pages")
	private int totalPagesNumber;

	@SerializedName("results")
	private List<ResultItem> results;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotalResultsNumber() {
		return totalResultsNumber;
	}

	public void setTotalResultsNumber(int totalResultsNumber) {
		this.totalResultsNumber = totalResultsNumber;
	}

	public int getTotalPagesNumber() {
		return totalPagesNumber;
	}

	public void setTotalPagesNumber(int totalPagesNumber) {
		this.totalPagesNumber = totalPagesNumber;
	}

	public List<ResultItem> getResults() {
		return results;
	}

	public void setResults(List<ResultItem> results) {
		this.results = results;
	}
}
