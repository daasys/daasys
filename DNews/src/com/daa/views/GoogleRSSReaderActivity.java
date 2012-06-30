package com.daa.views;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.daa.R;
import com.daa.delegate.INewsDelegate;
import com.daa.model.CategoryNews;
import com.daa.model.NewsAdapter;

public class GoogleRSSReaderActivity extends ListActivity implements INewsDelegate {
	private static final String TAG = "GoogleRSSReaderActivity";
	// views
	private ProgressDialog progressDialog;
	private NewsAdapter newsAdapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
	}

	/**
	 * Initialise views and send request for fetching news.
	 */
	private void initViews() {
		progressDialog = ProgressDialog.show(this, "DNews", "Please wait...",false, true);
		fetchNews();
	}

	private void fetchNews() {
		try {
			CategoryNews newsRequest = new CategoryNews();
			newsRequest.setContext(this);
			newsRequest.sendRequestForNews();
		} catch (Exception e) {
			Log.e(TAG, "Error occured " + e.getMessage());
		}
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i("test", "position " + position);
		Toast.makeText(this, "U click on position:: " + position, Toast.LENGTH_LONG).show();
	}

	@Override
	public void newsFetchedSuccess(ArrayList<CategoryNews> newsList) {
		dismisProgressDialog();

		if(newsList == null) return;
		newsAdapter = new NewsAdapter(this,newsList);
		setListAdapter(newsAdapter);
	}

	@Override
	public void newsFetchedFail(String errorMsg) {
		dismisProgressDialog();
		Toast.makeText(this, "Error while request", Toast.LENGTH_LONG);
	}
	
	/**
	 * Dismiss progress dialog.
	 */
	private void dismisProgressDialog() {
		if(progressDialog == null) return;
		if(progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
}