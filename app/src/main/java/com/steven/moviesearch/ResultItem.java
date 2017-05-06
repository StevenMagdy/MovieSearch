package com.steven.moviesearch;

/**
 * Created by steven on 12/23/16.
 */

public class ResultItem {
	private String title;
	private String year;
	private String type;
	private String imdbID;

	public ResultItem(String title, String year, String type, String imdbID) {
		this.title = title;
		this.year = year;
		this.type = type;
		this.imdbID = imdbID;
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getType() {
		return type;
	}

	public String getImdbID() {
		return imdbID;
	}
}
