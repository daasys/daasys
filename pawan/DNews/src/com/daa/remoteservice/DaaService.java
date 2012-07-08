/**
 * 
 */
package com.daa.remoteservice;

import java.util.ArrayList;
import java.util.concurrent.Future;

import com.daa.feedparser.RSSFeed;
import com.daa.feedparser.RSSLoader;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * @author DaaSys
 *
 */
public class DaaService extends Service {
	public ArrayList<RSSFeed> rssFeed = null;
	// links for fetching news.
	private String urlWorld = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk";
	private String urlSposts = "http://feeds.bbci.co.uk/sport/0/rss.xml?edition=int";
	private String uris[] = {"http://feeds.bbci.co.uk/news/education/rss.xml?edition=uk",urlWorld,urlSposts};
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new LocalBinder();
	}
	@Override
	public void onCreate() {
		rssFeed = new ArrayList<RSSFeed>();
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			fetchRSSFeed(uris);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	 
	public class LocalBinder extends Binder {
		public DaaService getService() {
            return DaaService.this;
        }
    }
	
	private void fetchRSSFeed(String[] uris) throws InterruptedException {
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
					category = "Education";
				} else if(index == 2) {
					category = "Sports";
				}
				rssFeed.add(feed);
				//use(feed,category);

			} catch (Exception e) {
				//dismisProgressDialog();
				// TODO: handle exception
			}
		}
		//dismisProgressDialog();
	}

}
