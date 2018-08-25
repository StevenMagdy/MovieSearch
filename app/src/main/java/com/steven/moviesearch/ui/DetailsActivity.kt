package com.steven.moviesearch.ui

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.steven.moviesearch.R
import com.steven.moviesearch.loaders.DetailsLoader
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*

class DetailsActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<ResultItem> {

	private var resultId = 0
	private var resultMediaType: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_details)
		if (intent.hasExtra("resultId")) {
			resultId = intent.getIntExtra("resultId", 0)
		}
		if (intent.hasExtra("resultMediaType")) {
			resultMediaType = intent.getStringExtra("resultMediaType")
		}
		supportLoaderManager.initLoader(2, null, this)
	}

	override fun onCreateLoader(id: Int, args: Bundle?): Loader<ResultItem> {
		return DetailsLoader(this, resultId, resultMediaType!!)
	}

	override fun onLoadFinished(loader: Loader<ResultItem>, data: ResultItem?) {
		if (data == null) return
		val titleYear: String?

		if (!data.year.isNullOrEmpty()) {
			titleYear = data.title + " (" + data.year + ")"
		} else {
			titleYear = data.title
		}
		if (!data.releaseDate.isNullOrEmpty()) {
			textView_releaseDate.text = data.releaseDate
		} else {
			textView_releaseDate.text = "-"
		}

		textView_title_year.text = titleYear

		textView_averageVote.text = data.averageVote.toString()

		if (data.genres != null && data.genres?.isNotEmpty() == true) {
			var comma = ""
			for ((_, name) in data.genres!!) {
				textView_genre.append(comma)
				textView_genre.append(name)
				comma = ", "
			}
		} else {
			textView_genre.text = "-"
		}

		textView_runtime.text = data.runtime.toString() + " min"
		textView_plot.text = data.overview

		if (!data.originalLanguage.isNullOrEmpty()) {
			textView_language.text = Locale(data.originalLanguage).displayLanguage
		} else {
			textView_language.text = "-"
		}

		if (data.isAdult == true) {
			textView_adult.visibility = View.VISIBLE
		}

		data.posterPath?.let { path: String ->
			Glide.with(this)
					.load("https://image.tmdb.org/t/p/w500$path")
					.apply(RequestOptions.centerCropTransform())
					.into(imageView_poster)
		} ?: imageView_poster.setImageDrawable(null)

	}

	override fun onLoaderReset(loader: Loader<ResultItem>) {
	}

	companion object {
		private val TAG = DetailsActivity::class.java.name
	}
}
