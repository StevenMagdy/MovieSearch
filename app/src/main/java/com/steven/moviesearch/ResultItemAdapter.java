package com.steven.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by steven on 12/24/16.
 */

public class ResultItemAdapter extends ArrayAdapter<ResultItem> {

	public ResultItemAdapter(Context context, ArrayList<ResultItem> resultList) {

		super(context, 0, resultList);
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ResultItem currentResultItem = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
		}

		TextView titleTextView = (TextView) convertView.findViewById(R.id.textView_title_year);
		titleTextView.setText(currentResultItem.getTitle()
				+ " (" + currentResultItem.getYear() + ")");

		TextView typeTextView = (TextView) convertView.findViewById(R.id.textView_type);
		typeTextView.setText(currentResultItem.getType());

		ImageView posterImageView = (ImageView) convertView.findViewById(R.id.imageView_poster);
		Glide.with(getContext())
				.load(currentResultItem.getPoster())
				.apply(RequestOptions.centerCropTransform())
				.into(posterImageView);

		return convertView;
	}
}
