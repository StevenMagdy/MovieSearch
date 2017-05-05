package com.steven.moviesearch;

/**
 * Created by steven on 12/23/16.
 */

public class ResultItem {
	private String title;
	private String year;
	private String type;
	private String id;

	public ResultItem(String title, String year, String type, String id) {
		this.title = title;
		this.year = year;
		this.type = type;
		this.id = id;
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

	public String getId() {
		return id;
	}
}
