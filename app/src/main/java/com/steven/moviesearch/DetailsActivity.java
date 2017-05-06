package com.steven.moviesearch;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<JSONObject> {

	private String url = "http://www.omdbapi.com/";
	private String imdbID;
	TextView titleTextView, yearTextView, ratedTextView, ratingTextView, genreTextView,
			runtimeTextView, plotTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		imdbID = getIntent().getStringExtra("imdbID");
		getLoaderManager().initLoader(2, null, this);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		yearTextView = (TextView) findViewById(R.id.yearTextView);
		ratedTextView = (TextView) findViewById(R.id.ratedTextView);
		ratingTextView = (TextView) findViewById(R.id.ratingTextView);
		genreTextView = (TextView) findViewById(R.id.genreTextView);
		runtimeTextView = (TextView) findViewById(R.id.runtimeTextView);
		plotTextView = (TextView) findViewById(R.id.plotTextView);


	}

	@Override
	public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
		Uri baseUri = Uri.parse(url);
		Uri.Builder builder = baseUri.buildUpon();
		builder.appendQueryParameter("i", imdbID);
		return new DetailsLoader(this, builder.toString());
	}

	@Override
	public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
		try {
			titleTextView.setText(data.getString("Title"));
			yearTextView.setText("(" + data.getString("Year") + ")");
			ratedTextView.setText(data.getString("Rated"));
			ratingTextView.setText(data.getString("imdbRating"));
			genreTextView.setText(data.getString("Genre"));
			runtimeTextView.setText(data.getString("Runtime"));
			plotTextView.setText(data.getString("Plot"));
		} catch (JSONException e) {

		}

	}

	@Override
	public void onLoaderReset(Loader<JSONObject> loader) {

	}
}
