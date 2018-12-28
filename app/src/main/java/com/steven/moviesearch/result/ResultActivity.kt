package com.steven.moviesearch.result

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import com.steven.moviesearch.detalis.DetailsActivity
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

  private val resultItemAdapter: ResultItemAdapter by lazy { ResultItemAdapter(this) }
  private var toast: Toast? = null
  private val viewModel: ResultViewModel by lazy {
    val vm = ViewModelProviders.of(this).get(ResultViewModel::class.java)
    vm.searchQuery = intent.getStringExtra("searchText")
    return@lazy vm
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_result)

    resultItemAdapter.onItemClickListener = fun(position: Int) {
      val item = resultItemAdapter.getItem(position) ?: return

      Utils.isNetworkAvailable(application) { isConnected: Boolean ->
        if (isConnected) {
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
    }
    val linearLayoutManager = LinearLayoutManager(this)
    resultRecyclerView.layoutManager = linearLayoutManager
    resultRecyclerView.adapter = resultItemAdapter
    showProgress()
    viewModel.result.observe(this, Observer<List<ResultItem>> { showResults(it) })
    if (viewModel.result.value == null) {
      viewModel.reLoad()
    }
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

  companion object {
    private val TAG = ResultActivity::class.java.name
  }
}
