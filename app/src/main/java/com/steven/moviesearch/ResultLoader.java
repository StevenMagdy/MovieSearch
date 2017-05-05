package com.steven.moviesearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by steven on 12/25/16.
 */

public class ResultLoader extends AsyncTaskLoader<ArrayList<ResultItem>> {

	private String url;

	public ResultLoader(Context context, String url) {
		super(context);
		this.url = url;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	public ArrayList<ResultItem> loadInBackground() {
		return Utils.fetchSearchResult(url);
	}


}
