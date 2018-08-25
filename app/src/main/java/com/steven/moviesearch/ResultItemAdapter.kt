package com.steven.moviesearch

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.steven.moviesearch.models.ResultItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.view.*

class ResultItemAdapter(private var context: Context) : Adapter<ResultItemAdapter.ResultItemHolder>() {
	private var resultItems: List<ResultItem>? = null
	var onItemClickListener: ((Int) -> Unit) = {}

	override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ResultItemHolder {
		return ResultItemHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout
				.list_item, viewGroup, false))
	}

	override fun onBindViewHolder(resultItemHolder: ResultItemHolder, i: Int) {
		val currentResultItem = resultItems?.get(i) ?: return
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
		return resultItems?.size ?: 0
	}

	fun swapResults(resultItems: List<ResultItem>?) {
		if (resultItems != null) {
			this.resultItems = resultItems
			notifyDataSetChanged()
		}
	}

	fun getItem(position: Int): ResultItem? {
		return resultItems?.get(position)
	}

	inner class ResultItemHolder(override val containerView: View)
		: ViewHolder(containerView), LayoutContainer, View.OnClickListener {
		init {
			itemView.setOnClickListener(this)
		}

		override fun onClick(v: View) {
			onItemClickListener(adapterPosition)
		}
	}

	companion object {
		private val TAG = ResultItemAdapter::class.java.name
	}

}
