package com.steven.moviesearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public final class Utils {

	private static final String TAG = Utils.class.getName();


	private Utils() {
	}

	public static ArrayList<ResultItem> fetchSearchResult(String urlString) {
		return extractResultFromJson(makeHttpRequest(urlString));
	}

	public static JSONObject fetchItemDetails(String urlString) {
		return extractDetailsFromJson(makeHttpRequest(urlString));
	}

	public static String makeHttpRequest(String urlString) {

		OkHttpClient client = new OkHttpClient();
		try {
			Request request = new Request.Builder().url(urlString).build();
			return client.newCall(request).execute().body().string();
		} catch (IOException e) {
			Log.e(TAG, "Error retrieving results", e);
			return null;
		}
	}

	public static ArrayList<ResultItem> extractResultFromJson(String stringResponse) {

		try {
			ArrayList<ResultItem> resultArray = new ArrayList<>();
			JSONObject baseJsonResponse = new JSONObject(stringResponse);
			JSONArray jsonResultArray = baseJsonResponse.getJSONArray("results");
			for (int i = 0; i < jsonResultArray.length(); i += 1) {
				JSONObject jsonCurrentResultItem = jsonResultArray.getJSONObject(i);
				String currentResultTitle = jsonCurrentResultItem.getString("title");
				String currentResultYear = jsonCurrentResultItem.getString("release_date");
				String currentResultType = "";
				// switch (jsonCurrentResultItem.getString("Type")) {
				// 	case "movie":
				currentResultType = "Movie";
				// break;
				// case "series":
				// 	currentResultType = "Series";
				// 	break;
				// case "episode":
				// 	currentResultType = "Episode";
				// 	break;
				// }
				String currentResultId = jsonCurrentResultItem.getString("id");
				String currentResultPoster = jsonCurrentResultItem.getString("poster_path");
				resultArray.add(new ResultItem(currentResultTitle, currentResultYear,
						currentResultType, currentResultId, currentResultPoster));
			}
			return resultArray;
		} catch (JSONException e) {
			Log.e(TAG, "Error Parsing JSON", e);
			return null;
		}
	}

	public static JSONObject extractDetailsFromJson(String stringResponse) {
		try {
			return new JSONObject(stringResponse);
		} catch (JSONException e) {
			Log.e(TAG, "Error Parsing JSON", e);
			return null;
		}
	}
}
