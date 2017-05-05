package com.steven.moviesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

	private EditText searchEditText;
	private ImageButton searchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, ResultActivity.class);
				i.putExtra("searchText", searchEditText.getText().toString());
				startActivity(i);
			}
		});
	}
}
