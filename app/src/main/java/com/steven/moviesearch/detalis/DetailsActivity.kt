package com.steven.moviesearch.detalis

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.steven.moviesearch.R
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.synthetic.main.activity_details.*
import java.util.*

class DetailsActivity : AppCompatActivity() {

  private var resultId = 0
  private var resultMediaType: String? = null
  private val viewModel: DetailsViewModel by lazy {
    val vm = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
    vm.resultId = intent?.getIntExtra("resultId", -1) ?: -1
    vm.resultMediaType = intent.getStringExtra("resultMediaType")
    return@lazy vm
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_details)
    if (intent.hasExtra("resultId")) {
      resultId = intent.getIntExtra("resultId", 0)
    }
    if (intent.hasExtra("resultMediaType")) {
      resultMediaType = intent.getStringExtra("resultMediaType")
    }
    viewModel.resultItem.observe(this, Observer<ResultItem> { setData(it) })
    if (viewModel.resultItem.value == null) {
      viewModel.reload()
    }
  }

  private fun setData(data: ResultItem?) {
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

  companion object {
    private val TAG = DetailsActivity::class.java.name
  }
}
