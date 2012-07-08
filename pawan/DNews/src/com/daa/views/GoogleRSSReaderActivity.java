package com.daa.views;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.daa.R;
import com.daa.delegate.INewsDelegate;
import com.daa.feedparser.RSSFeed;
import com.daa.feedparser.RSSItem;
import com.daa.feedparser.RSSLoader;
import com.daa.model.CategoryNews;
import com.daa.model.FeedAdapter;
import com.daa.model.NewsAdapter;
import com.daa.remoteservice.DaaService;

public class GoogleRSSReaderActivity extends ListActivity implements INewsDelegate {
	private static final String TAG = "GoogleRSSReaderActivity";
	// views
	private ProgressDialog progressDialog;
	private NewsAdapter newsAdapter;
	private FeedAdapter adapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//progressDialog = ProgressDialog.show(this, "DNews", "Please wait...",false, true);
		initViews();
	}

	/**
	 * Initialise views and send request for fetching news.
	 */
	private void initViews() {
		adapter = new FeedAdapter(this,null,null);
		getListView().setEmptyView(findViewById(R.id.TextViewEmpty));
		setListAdapter(adapter);
		//String uris[] =  {"http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk"};//{"http://feeds.reuters.com/reuters/environment"};// http://feeds.bbci.co.uk/news/education/rss.xml?edition=uk
		String urlWorld = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk"; //https://www.bbc.co.uk/news/business-18732714#sa-ns_mchannel=rss&amp;ns_source=PublicRSS20-sa
		String urlSports = "http://feeds.bbci.co.uk/sport/0/rss.xml?edition=int";
		String urlTechnology = "http://feeds.bbci.co.uk/news/technology/rss.xml?edition=uk";
		String urlScience = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml?edition=int";
		String urlEducation = "http://feeds.bbci.co.uk/news/education/rss.xml?edition=uk";
		String uris[] = {urlWorld,urlSports,urlTechnology};
		try {
			fetchRSSFeed(uris);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fetchNews();
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

	void fetchRSSFeed(String[] uris) throws InterruptedException {
		RSSLoader loader = RSSLoader.fifo();
		for (String uri : uris) {
			loader.load(uri);
		}

		RSSFeed feed;
		Future future;//&lt;

		for (int index = 0; index < uris.length; index++) {
			future = loader.take();
			try {
				feed = (RSSFeed) future.get();
				String category = "";
				if(index == 0) {
					category = "World";
				} else if(index == 1) {
					category = "Sports";
				} else if(index == 2) {
					category = "Technology";
				}else if(index == 3) {
					category = "Science";
				}else if(index == 4) {
					category = "Education";
				}
				use(feed,category);

			} catch (Exception e) {
				 
			}
		}
	}

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
		dismisProgressDialog();

		if(newsList == null) return;
		newsAdapter = new NewsAdapter(this, newsList);
		setListAdapter(newsAdapter);
		newsAdapter.notifyDataSetChanged();
		//refreshListView();
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


	DaaService mBoundService;
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service.  Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			mBoundService = ((DaaService.LocalBinder)service).getService();
			ArrayList<RSSFeed> feeds = mBoundService.rssFeed;
			for (int index = 0; index < feeds.size(); index++) {
				use(feeds.get(index),"category");
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			mBoundService = null;
		}
	};
	
	private boolean mIsBound;

	void doBindService() {
		// Establish a connection with the service.  We use an explicit
		// class name because we want a specific service implementation that
		// we know will be running in our own process (and thus won't be
		// supporting component replacement by other applications).
		bindService(new Intent(this, 
				DaaService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			unbindService(mConnection);
			mIsBound = false;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		doUnbindService();
	}

}