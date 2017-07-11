package com.steven.moviesearch;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.steven.moviesearch.models.ResultItem;

import java.util.List;

public class ResultActivity extends AppCompatActivity implements LoaderManager
		.LoaderCallbacks<List<ResultItem>> {

	private static final String TAG = ResultActivity.class.getName();

	private TextView resultTextView;
	private ResultItemAdapter resultItemAdapter;
	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	private String searchText;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		if (getIntent().hasExtra("searchText")) {
			searchText = getIntent().getStringExtra("searchText");
		}
		resultItemAdapter = new ResultItemAdapter(this);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		recyclerView = (RecyclerView) findViewById(R.id.resultRecyclerView);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);

		resultItemAdapter.setOnItemClickListener(new ResultItemAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				if (Utils.isNetworkAvailable(ResultActivity.this)) {
					Intent i = new Intent(ResultActivity.this, DetailsActivity.class);
					i.putExtra("resultId", resultItemAdapter.getItem(position).getId());
					startActivity(i);
				} else {
					toast = Toast.makeText(ResultActivity.this, "Network Not Available", Toast
							.LENGTH_SHORT);
					toast.show();
				}
			}
		});
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
		recyclerView.setAdapter(resultItemAdapter);
		showProgress();
		getLoaderManager().initLoader(1, null, this);
	}

	private void showMessage(String message) {
		recyclerView.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
		resultTextView.setVisibility(View.VISIBLE);
		resultTextView.setText(message);
	}

	private void showProgress() {
		recyclerView.setVisibility(View.GONE);
		resultTextView.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
	}

	private void showResults(List<ResultItem> resultItems) {
		resultTextView.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
		recyclerView.setVisibility(View.VISIBLE);
		resultItemAdapter.swapResults(resultItems);
	}

	@Override
	public Loader<List<ResultItem>> onCreateLoader(int id, Bundle args) {
		return new ResultLoader(this, searchText);
	}

	@Override
	public void onLoadFinished(Loader<List<ResultItem>> loader, List<ResultItem> data) {

		if (data == null) {
			showMessage("Error");
		} else if (data.size() == 0) {
			showMessage("No Result");
		} else {
			showResults(data);
		}
	}

	@Override
	public void onLoaderReset(Loader<List<ResultItem>> loader) {
	}
}
