package com.steven.moviesearch.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.steven.moviesearch.R
import com.steven.moviesearch.ResultItemAdapter
import com.steven.moviesearch.Utils
import com.steven.moviesearch.loaders.ResultLoader
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), LoaderCallbacks<List<ResultItem>?> {

	private val resultItemAdapter: ResultItemAdapter by lazy { ResultItemAdapter(this) }
	private var searchText: String? = null
	private var toast: Toast? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_result)
		if (intent.hasExtra("searchText")) {
			searchText = intent.getStringExtra("searchText")
		}

		resultItemAdapter.onItemClickListener = fun(position: Int) {
			val item = resultItemAdapter.getItem(position) ?: return
			if (Utils.isNetworkAvailable(this@ResultActivity)) {
				val intent = Intent(this@ResultActivity, DetailsActivity::class.java)
				intent.putExtra("resultId", item.id)
				intent.putExtra("resultMediaType", item.mediaType)
				startActivity(intent)
			} else {
				toast?.cancel()
				toast = Toast.makeText(this@ResultActivity, "Network Not Available", Toast
						.LENGTH_SHORT)
				toast?.show()
			}
		}

		val linearLayoutManager = LinearLayoutManager(this)
		resultRecyclerView.layoutManager = linearLayoutManager
		resultRecyclerView.adapter = resultItemAdapter
		showProgress()
		supportLoaderManager.initLoader(1, null, this)
	}

	private fun showMessage(message: String) {
		resultRecyclerView.visibility = View.GONE
		progress_bar.visibility = View.GONE
		resultTextView.visibility = View.VISIBLE
		resultTextView.text = message
	}

	private fun showProgress() {
		resultRecyclerView.visibility = View.GONE
		resultTextView.visibility = View.GONE
		progress_bar.visibility = View.VISIBLE
	}

	private fun showResults(resultItems: List<ResultItem>) {
		resultTextView.visibility = View.GONE
		progress_bar.visibility = View.GONE
		resultRecyclerView.visibility = View.VISIBLE
		resultItemAdapter.swapResults(resultItems)
	}

	override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<ResultItem>?> {
		return ResultLoader(this, searchText!!)
	}

	override fun onLoadFinished(loader: Loader<List<ResultItem>?>, data: List<ResultItem>?) {
		when {
			data == null -> showMessage("Error")
			data.isEmpty() -> showMessage("No Result")
			else -> showResults(data)
		}
	}

	override fun onLoaderReset(loader: Loader<List<ResultItem>?>) {

	}

	companion object {
		private val TAG = ResultActivity::class.java.name
	}
}
