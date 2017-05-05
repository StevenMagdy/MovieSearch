package com.steven.moviesearch;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<ArrayList<ResultItem>> {

	private String url = "http://www.omdbapi.com/";
	private TextView resultTextView;
	private ResultItemAdapter resultItemAdapter;
	private ArrayList<ResultItem> list;
	private ListView listView;
	private String searchText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		searchText = getIntent().getStringExtra("searchText").trim();
		resultItemAdapter = new ResultItemAdapter(this, new ArrayList<ResultItem>());
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		listView = (ListView) findViewById(R.id.resultListView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent i = new Intent(ResultActivity.this, DetailsActivity.class);
				i.putExtra("id", list.get(position).getId());
				startActivity(i);
			}
		});
		listView.setAdapter(resultItemAdapter);
		getLoaderManager().initLoader(1, null, this);
	}

	@Override
	public Loader<ArrayList<ResultItem>> onCreateLoader(int id, Bundle args) {

		Uri baseUri = Uri.parse(url);
		Uri.Builder builder = baseUri.buildUpon();
		builder.appendQueryParameter("s", searchText);

		return new ResultLoader(this, builder.toString());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<ResultItem>> loader, ArrayList<ResultItem> data) {
		if (data != null) {
			list = data;
			resultItemAdapter.addAll(list);
		} else {
			resultTextView.setText("Wrong Input");
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ResultItem>> loader) {
		resultItemAdapter.clear();
	}
}
