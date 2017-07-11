package com.steven.moviesearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.steven.moviesearch.models.ResultItem;

import java.util.List;

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.ResultItemHolder> {

	private static final String TAG = ResultItemAdapter.class.getName();
	private List<ResultItem> resultItems;
	private OnItemClickListener onItemClickListener;
	Context context;

	public interface OnItemClickListener {

		void onItemClick(int position);
	}

	public ResultItemAdapter(Context context) {
		this.context = context;
	}

	@Override
	public ResultItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		return new ResultItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
				.list_item, viewGroup, false));
	}

	@Override
	public void onBindViewHolder(ResultItemHolder resultItemHolder, int i) {
		String titleYear;
		ResultItem currentResultItem = resultItems.get(i);
		if (!TextUtils.isEmpty(currentResultItem.getReleaseDate())) {
			titleYear = currentResultItem.getTitle() + " (" + currentResultItem.getYear() + ")";
		} else {
			titleYear = currentResultItem.getTitle();
		}
		resultItemHolder.titleTextView.setText(titleYear);
		Glide.with(context)
				.load("https://image.tmdb.org/t/p/w500" + currentResultItem.getPosterPath())
				.apply(RequestOptions.centerCropTransform())
				.into(resultItemHolder.posterImageView);
	}

	@Override
	public int getItemCount() {
		return resultItems.size();
	}

	public void swapResults(List<ResultItem> resultItems) {
		if (resultItems != null) {
			this.resultItems = resultItems;
			notifyDataSetChanged();
		}
	}

	public ResultItem getItem(int position) {
		return resultItems.get(position);
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	class ResultItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView titleTextView;
		private ImageView posterImageView;

		public ResultItemHolder(View itemView) {
			super(itemView);
			titleTextView = (TextView) itemView.findViewById(R.id.textView_title_year);
			posterImageView = (ImageView) itemView.findViewById(R.id.imageView_poster);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			onItemClickListener.onItemClick(getAdapterPosition());
		}
	}

}
