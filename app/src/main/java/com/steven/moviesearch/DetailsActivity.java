package com.steven.moviesearch;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.steven.moviesearch.models.ResultItem;

public class DetailsActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<ResultItem> {

	private static final String TAG = DetailsActivity.class.getName();

	private int resultId;
	TextView titleYearTextView, ratedTextView, imdbRatingTextView, genreTextView,
			runtimeTextView, plotTextView, writerTextView, actorsTextView, directorTextView,
			languageTextView;
	ImageView posterImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		if (getIntent().hasExtra("resultId")) {
			resultId = getIntent().getIntExtra("resultId", 0);
		}

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
		getLoaderManager().initLoader(2, null, this);
	}

	@Override
	public Loader<ResultItem> onCreateLoader(int id, Bundle args) {

		return new DetailsLoader(this, resultId);
	}

	@Override
	public void onLoadFinished(Loader<ResultItem> loader, ResultItem data) {

		String titleYear = data.getTitle() + " (" + data.getYear() + ")";
		titleYearTextView.setText(titleYear);
		// ratedTextView.setText(data.getString("Rated"));
		imdbRatingTextView.setText(String.valueOf(data.getVoteAverage()));
		// genreTextView.setText(data.getString("Genre"));
		runtimeTextView.setText(String.valueOf(data.getRuntime()) + " min");
		plotTextView.setText(data.getOverview());
		// writerTextView.setText(data.getString("Writer"));
		// actorsTextView.setText(data.getString("Actors"));
		// directorTextView.setText(data.getString("Director"));
		languageTextView.setText(data.getOriginalLanguage());
		Glide.with(this)
				.load("https://image.tmdb.org/t/p/w500" + data.getPosterPath())
				.apply(RequestOptions.centerCropTransform())
				.into(posterImageView);

	}

	@Override
	public void onLoaderReset(Loader<ResultItem> loader) {

	}
}
