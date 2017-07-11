package com.steven.moviesearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.steven.moviesearch.R;
import com.steven.moviesearch.Utils;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getName();

	private EditText searchEditText;
	private ImageButton searchButton;
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Utils.isNetworkAvailable(MainActivity.this)) {
					Intent i = new Intent(MainActivity.this, ResultActivity.class);
					i.putExtra("searchText", searchEditText.getText().toString().trim());
					startActivity(i);
				} else {
					toast = Toast.makeText(MainActivity.this, "Network Not Available", Toast
							.LENGTH_SHORT);
					toast.show();

				}
			}
		});
	}
}
