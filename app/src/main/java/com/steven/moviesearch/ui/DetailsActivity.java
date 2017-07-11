package com.steven.moviesearch.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.steven.moviesearch.loaders.DetailsLoader;
import com.steven.moviesearch.R;
import com.steven.moviesearch.models.Genre;
import com.steven.moviesearch.models.ResultItem;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<ResultItem> {

	private static final String TAG = DetailsActivity.class.getName();

	private int resultId;
	TextView titleYearTextView, adultTextView, averageVoteTextView, genreTextView,
			runtimeTextView, plotTextView, languageTextView, releaseDateTextView;
	ImageView posterImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		if (getIntent().hasExtra("resultId")) {
			resultId = getIntent().getIntExtra("resultId", 0);
		}
		titleYearTextView = (TextView) findViewById(R.id.textView_title_year);
		adultTextView = (TextView) findViewById(R.id.textView_adult);
		averageVoteTextView = (TextView) findViewById(R.id.textView_averageVote);
		genreTextView = (TextView) findViewById(R.id.textView_genre);
		runtimeTextView = (TextView) findViewById(R.id.textView_runtime);
		plotTextView = (TextView) findViewById(R.id.textView_plot);
		languageTextView = (TextView) findViewById(R.id.textView_language);
		releaseDateTextView = (TextView) findViewById(R.id.textView_releaseDate);
		posterImageView = (ImageView) findViewById(R.id.imageView_poster);
		getLoaderManager().initLoader(2, null, this);
	}

	@Override
	public Loader<ResultItem> onCreateLoader(int id, Bundle args) {
		return new DetailsLoader(this, resultId);
	}

	@Override
	public void onLoadFinished(Loader<ResultItem> loader, ResultItem data) {

		String titleYear;

		if (!TextUtils.isEmpty(data.getReleaseDate())) {
			titleYear = data.getTitle() + " (" + data.getYear() + ")";
			releaseDateTextView.setText(data.getReleaseDate());
		} else {
			releaseDateTextView.setText("-");
			titleYear=data.getTitle();
		}

		titleYearTextView.setText(titleYear);

		averageVoteTextView.setText(String.valueOf(data.getAverageVote()));

		if (data.getGenres() != null && data.getGenres().size() > 0) {
			String comma = "";
			for (Genre genre : data.getGenres()) {
				genreTextView.append(comma);
				genreTextView.append(genre.getName());
				comma = ", ";
			}
		} else {
			genreTextView.setText("-");
		}

		runtimeTextView.setText(String.valueOf(data.getRuntime()) + " min");

		plotTextView.setText(data.getOverview());

		if (!TextUtils.isEmpty(data.getOriginalLanguage())) {
			languageTextView.setText(new Locale(data.getOriginalLanguage()).getDisplayLanguage());
		} else {
			languageTextView.setText("-");
		}

		if (data.isAdult()) {
			adultTextView.setVisibility(View.VISIBLE);
		}

		Glide.with(this)
				.load("https://image.tmdb.org/t/p/w500" + data.getPosterPath())
				.apply(RequestOptions.centerCropTransform())
				.into(posterImageView);
	}

	@Override
	public void onLoaderReset(Loader<ResultItem> loader) {

	}
}
