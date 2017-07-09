package com.steven.moviesearch;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<JSONObject> {

	private static final String TAG = DetailsActivity.class.getName();

	private String url = "https://api.themoviedb.org/3/movie";
	private String imdbID;
	TextView titleYearTextView, ratedTextView, imdbRatingTextView, genreTextView,
			runtimeTextView, plotTextView, writerTextView, actorsTextView, directorTextView,
			languageTextView;
	ImageView posterImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		if (getIntent().hasExtra("imdbID")) {
			imdbID = getIntent().getStringExtra("imdbID");
		}
		getLoaderManager().initLoader(2, null, this);
		titleYearTextView = (TextView) findViewById(R.id.textView_title_year);
		ratedTextView = (TextView) findViewById(R.id.textView_rated);
		imdbRatingTextView = (TextView) findViewById(R.id.textView_imdbRating);
		genreTextView = (TextView) findViewById(R.id.textView_genre);
		runtimeTextView = (TextView) findViewById(R.id.textView_runtime);
		plotTextView = (TextView) findViewById(R.id.textView_plot);
		writerTextView = (TextView) findViewById(R.id.textView_writer);
		actorsTextView = (TextView) findViewById(R.id.textView_actors);
		directorTextView = (TextView) findViewById(R.id.textView_director);
		languageTextView = (TextView) findViewById(R.id.textView_language);
		posterImageView = (ImageView) findViewById(R.id.imageView_poster);

	}

	@Override
	public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
		Uri baseUri = Uri.parse(url);
		Uri.Builder builder = baseUri.buildUpon();
		builder.appendPath(imdbID);
		builder.appendQueryParameter("api_key", getString(R.string.theMovieDB_api_key));
		Log.v(TAG,builder.toString());
		return new DetailsLoader(this, builder.toString());
	}

	@Override
	public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
		try {
			String titleYear = data.getString("title") + " (" + data.getString("release_date") + ")";
			titleYearTextView.setText(titleYear);
			// ratedTextView.setText(data.getString("Rated"));
			imdbRatingTextView.setText(data.getString("vote_average"));
			// genreTextView.setText(data.getString("Genre"));
			runtimeTextView.setText(data.getString("runtime") + " min");
			plotTextView.setText(data.getString("overview"));
			// writerTextView.setText(data.getString("Writer"));
			// actorsTextView.setText(data.getString("Actors"));
			// directorTextView.setText(data.getString("Director"));
			languageTextView.setText(data.getString("original_language"));
			Glide.with(this)
					.load("https://image.tmdb.org/t/p/w500" + data.getString("poster_path"))
					.apply(RequestOptions.centerCropTransform())
					.into(posterImageView);

		} catch (JSONException e) {

		}

	}

	@Override
	public void onLoaderReset(Loader<JSONObject> loader) {

	}
}
