package com.steven.moviesearch.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.steven.moviesearch.R
import com.steven.moviesearch.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private var toast: Toast? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		searchButton.setOnClickListener {
			if (Utils.isNetworkAvailable(this@MainActivity)) {
				val i = Intent(this@MainActivity, ResultActivity::class.java)
				i.putExtra("searchText", searchEditText.text.toString().trim())
				startActivity(i)
			} else {
				toast?.cancel()
				toast = Toast.makeText(this@MainActivity, "Network Not Available", Toast
						.LENGTH_SHORT)
				toast?.show()

			}
		}
	}

	companion object {
		private val TAG = MainActivity::class.java.name
	}
}
