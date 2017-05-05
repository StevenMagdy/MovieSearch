package com.steven.moviesearch;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONObject;

/**
 * Created by steven on 12/30/16.
 */

public class DetailsLoader extends AsyncTaskLoader<JSONObject> {

	private String url;

	public DetailsLoader(Context context, String url) {
		super(context);
		this.url = url;
	}

	@Override
	public JSONObject loadInBackground() {
		return Utils.fetchItemDetails(url);
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}
}
