package com.steven.moviesearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

		TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
		titleTextView.setText(currentResultItem.getTitle());

		TextView yearTextView = (TextView) convertView.findViewById(R.id.yearTextView);
		yearTextView.setText(currentResultItem.getYear());

		TextView typeTextView = (TextView) convertView.findViewById(R.id.typeTextView);
		typeTextView.setText(currentResultItem.getType());

		return convertView;
	}
}
