package com.daa.news.views;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daa.news.R;
import com.daa.feedparser.RSSFeed;
import com.daa.feedparser.RSSItem;
import com.daa.feedparser.RSSReader;
import com.daa.feedparser.RSSReaderException;
import com.daa.news.delegate.INewsDelegate;
import com.daa.news.model.CategoryNews;
import com.daa.news.model.FeedAdapter;
import com.daa.news.pref.DaaPrefs;
import com.daa.news.util.Utility;

public class DNewsActivity extends ListActivity implements INewsDelegate, OnClickListener {
	private static final String TAG = "GoogleRSSReaderActivity";
	// views
	private RelativeLayout header;
	private ViewGroup progressBar;
	private FeedAdapter adapter;

	HashMap<String, RSSFeed> map = new HashMap<String, RSSFeed>();;

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
		header = (RelativeLayout) findViewById(R.id.header);
		progressBar = (ViewGroup)findViewById(R.id.progressbar);

		ImageView imgViewSetting = (ImageView) header.findViewById(R.id.ImageViewRight);
		TextView txtViewTitle = (TextView)header.findViewById(R.id.TextViewHeading);
		txtViewTitle.setText("Last update ...");

		imgViewSetting.setOnClickListener(this);

		adapter = new FeedAdapter(this,null,null);
		getListView().setEmptyView(findViewById(R.id.TextViewEmpty));

		setListAdapter(adapter);
		setUpNewsHeadlines();

	}

	private void setUpNewsHeadlines() {
		String category = "HeadLines";
		String url = DaaPrefs.getDaaPrefs(this).getHeadLines();
		if(url == null) {
			Toast.makeText(this, R.string.selectCategory, Toast.LENGTH_LONG).show();
			return;
		}

		try {
			if(!Utility.isNetworkAvailable(this)) {
				Utility.showMSG(this, this.getString(R.string.internetError));
				return;
			}
			// fetch news from web service.
			fetchRSSFeed(category, url);
		} catch (InterruptedException e) {
			Toast.makeText(this, R.string.errorMsg, Toast.LENGTH_LONG).show();
		} catch ( Exception e) {
			Toast.makeText(this, R.string.errorMsg, Toast.LENGTH_LONG).show();
		}
	}

	void fetchRSSFeed(final String category, final String url) throws InterruptedException {
		if(progressBar != null) {
			progressBar.setVisibility(View.VISIBLE);
		}

		Thread t = new Thread() {
			@Override
			public void run() {
				super.run();
				RSSReader reader = new RSSReader();
				try {
					RSSFeed feed = reader.load(url);
					if(feed != null) {
						map.put(category, feed);
					}
					Bundle data = new Bundle();
					data.putString("data", category);
					Message msg = new Message();
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (RSSReaderException e) {
					Log.e(TAG + "fetchRSSFeed() ", "ERROR ::"+e.getMessage());
				} catch (Exception e) {
					Log.e(TAG + "fetchRSSFeed() ", "ERROR ::"+e.getMessage());
				}
			}
		};
		t.setName("Main rss loader thread");
		t.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			try {
				if(progressBar != null) {
					progressBar.setVisibility(View.GONE);
				}
				String category = msg.getData().getString("data");
				if(map.containsKey(category)) {
					RSSFeed feed = map.get(category);
					use(feed,category);
				}
			} catch (Exception e) {
				String error = getResources().getString(R.string.errorMsg);
				Toast.makeText(DNewsActivity.this, R.string.errorMsg, Toast.LENGTH_LONG).show();
				Log.e(TAG + "handler", error + e.getMessage());
			}
		};
	};

	private void use(RSSFeed feed, String category) {

		if(feed == null) return;
		List<RSSItem> feedItem = feed.getItems();
		if(adapter == null) {
			return;
		}
		adapter.addSeparatorItem(category);
		adapter.addItem(feedItem);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i("test", "position " + position);
		Toast.makeText(this, "U click on position:: " + position, Toast.LENGTH_LONG).show();
	}

	@Override
	public void newsFetchedSuccess(ArrayList<CategoryNews> newsList) {

	}

	@Override
	public void newsFetchedFail(String errorMsg) {
		Toast.makeText(this, "Error while request", Toast.LENGTH_LONG);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ImageViewRight:
			Intent intent = new Intent(this, SettingActivity.class);
			startActivityForResult(intent, 100);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK) {

			Bundle bundle = data.getExtras();
			ArrayList<String> categories = bundle.getStringArrayList("selected");
			DaaPrefs pref = DaaPrefs.getDaaPrefs(this);
			HashMap<String, String> list = pref.getSelectedCategories(categories);

			Set<String> keys  = list.keySet();
			// clear previous data
			adapter.clearItem();
			if(!Utility.isNetworkAvailable(this)) {
				Utility.showMSG(this, this.getString(R.string.internetError));
				return;
			}


			for (String key : keys) {
				String categoryURL = list.get(key);
				try {
					fetchRSSFeed(key, categoryURL);
				} catch (Exception e) {
					Log.e(TAG + "onActivityResult()", "An error occured " + e.getMessage());
				}
			}
		}
	}

}