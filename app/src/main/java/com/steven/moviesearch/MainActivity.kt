package com.steven.moviesearch

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.steven.moviesearch.result.ResultActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  private var toast: Toast? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    searchButton.setOnClickListener {
      Utils.isNetworkAvailable(application) { isConnected: Boolean ->
        if (isConnected) {
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
  }

  companion object {
    private val TAG = MainActivity::class.java.name
  }
}
