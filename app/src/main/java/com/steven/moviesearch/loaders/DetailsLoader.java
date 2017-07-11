package com.steven.moviesearch.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.steven.moviesearch.EndPoints;
import com.steven.moviesearch.R;
import com.steven.moviesearch.Utils;
import com.steven.moviesearch.models.ResultItem;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class DetailsLoader extends AsyncTaskLoader<ResultItem> {

	private static final String TAG = DetailsLoader.class.getName();

	private int resultId;
	private Context context;

	public DetailsLoader(Context context, int resultId) {
		super(context);
		this.context = context;
		this.resultId = resultId;
	}

	@Override
	public ResultItem loadInBackground() {
		OkHttpClient okHttpClient = Utils.provideOkHttpClient();
		Retrofit retrofit = Utils.provideRetrofit(okHttpClient);
		EndPoints endPoints = retrofit.create(EndPoints.class);
		Call<ResultItem> resultItemCall =
				endPoints.getMovieDetails(resultId, context.getString(R.string.theMovieDB_api_key));
		try {
			return resultItemCall.execute().body();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}
}
