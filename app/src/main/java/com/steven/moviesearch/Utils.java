package com.steven.moviesearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by steven on 12/24/16.
 */

public final class Utils {

	private static final String LOG_TAG = Utils.class.getName();


	private Utils() {
	}

	public static ArrayList<ResultItem> fetchSearchResult(String url) {
		return extractResultFromJson(makeHttpRequest(makeUrl(url)));
	}

	public static JSONObject fetchItemDetails(String url) {
		return extractDetailsFromJson(makeHttpRequest(makeUrl(url)));
	}


	public static String makeHttpRequest(URL url) {

		try {
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.connect();
			InputStream inputStream = httpURLConnection.getInputStream();
			StringBuilder stringBuilder = new StringBuilder();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = bufferedReader.readLine();

			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
			return stringBuilder.toString();
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error retrieving results", e);
			return null;
		}
	}

	public static URL makeUrl(String stringUrl) {

		try {
			return new URL(stringUrl);
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error creating URL", e);
			return null;
		}
	}

	public static ArrayList<ResultItem> extractResultFromJson(String stringResponse) {

		try {
			ArrayList<ResultItem> resultArray = new ArrayList<>();
			JSONObject baseJsonResponse = new JSONObject(stringResponse);
			JSONArray jsonResultArray = baseJsonResponse.getJSONArray("Search");
			for (int i = 0; i < jsonResultArray.length(); i += 1) {
				JSONObject jsonCurrentResultItem = jsonResultArray.getJSONObject(i);
				String currentResultTitle = jsonCurrentResultItem.getString("Title");
				String currentResultYear = jsonCurrentResultItem.getString("Year");
				String currentResultType = jsonCurrentResultItem.getString("Type");
				String currentResultId = jsonCurrentResultItem.getString("imdbID");
				resultArray.add(new ResultItem(currentResultTitle, currentResultYear,
						currentResultType, currentResultId));
			}
			return resultArray;
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Error Parsing JSON", e);
			return null;
		}
	}

	public static JSONObject extractDetailsFromJson(String stringResponse) {
		try {
			return new JSONObject(stringResponse);
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Error Parsing JSON", e);
			return null;
		}
	}
}
