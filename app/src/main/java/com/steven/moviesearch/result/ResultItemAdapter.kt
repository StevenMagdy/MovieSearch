package com.steven.moviesearch.result

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.steven.moviesearch.R
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class ResultItemAdapter(private var context: Context, private var items: List<ResultItem> = emptyList()) : Adapter<ResultItemAdapter.ResultItemHolder>() {

  var onItemClickListener: (Int) -> Unit = {}

  override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ResultItemHolder {
    return ResultItemHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false))
  }

  override fun onBindViewHolder(resultItemHolder: ResultItemHolder, i: Int) {
    val currentResultItem = items[i]
    val titleYear: String?
    if (!currentResultItem.year.isNullOrEmpty()) {
      titleYear = currentResultItem.title + " (" + currentResultItem.year + ")"
    } else {
      titleYear = currentResultItem.title
    }
    resultItemHolder.containerView.textView_title_year?.text = titleYear
    when (currentResultItem.mediaType) {
      "movie" -> resultItemHolder.containerView.textView_type?.text = "Movie"
      "tv" -> resultItemHolder.containerView.textView_type?.text = "TV Series"
    }


    currentResultItem.posterPath?.let { path: String ->
      Glide.with(context)
          .load("https://image.tmdb.org/t/p/w500$path")
          .apply(RequestOptions.centerCropTransform())
          .into(resultItemHolder.containerView.imageView_poster)
    } ?: resultItemHolder.containerView.imageView_poster.setImageDrawable(null)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun swapResults(resultItems: List<ResultItem>) {
    this.items = resultItems
    notifyDataSetChanged()
  }

  fun getItem(position: Int): ResultItem? {
    return items[position]
  }

  inner class ResultItemHolder(override val containerView: View)
    : ViewHolder(containerView), LayoutContainer, View.OnClickListener {
    init {
      containerView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
      onItemClickListener(adapterPosition)
    }
  }

  companion object {
    private val TAG = ResultItemAdapter::class.java.name
  }

}
