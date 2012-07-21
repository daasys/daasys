package com.daa.news.model;

import java.util.ArrayList;

import org.w3c.dom.Element;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.daa.news.delegate.INewsDelegate;
import com.daa.news.delegate.IServiceDelegate;
import com.daa.news.service.DaaService;
import com.daa.news.util.DataManager;
import com.daa.news.util.FeedParser;

/**
 * 
 * @author DaaSys
 *
 */
public class CategoryNews implements IServiceDelegate {

	private static final String TAG = "CategoryNews";
	private static final int FETCH_NEWS = 100;
	private static final int FETCH_IMAGE = 200;

	private String category;
	private ArrayList<News> newsList;
	private Context context;

	public void sendRequestForNews() {
		try {
			DaaService service = new DaaService();
			service.setDelegate(this);
			service.tag = FETCH_NEWS;
			// send web service request.
			service.sendNewsRequest();
		} catch (Exception e) {
			Log.e(TAG,"Error occured " + e.getMessage());
		}
	}

	/**
	 * Fetch image of news.
	 * @param detailLink
	 */
	public Bitmap fetchImage(String detailLink) {
		try {
			DaaService service = new DaaService();
			service.setDelegate(this);
			service.tag = FETCH_IMAGE;
			// send web service request.
			Bitmap bitMap = service.fetchImage(detailLink);
			DataManager.bitMap = bitMap;
			return bitMap;
		} catch (Exception e) {
			Log.e(TAG,"Error occured " + e.getMessage());
			return null;
		}
	}


	// getter and setter
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public ArrayList<News> getNewsList() {
		return newsList;
	}
	public void setNewsList(ArrayList<News> newsList) {
		this.newsList = newsList;
	}

	@Override
	public void onSuccess(Element rootElement, int serviceCode) {
		Toast.makeText(this.context, "Success", Toast.LENGTH_LONG);
		FeedParser parser = new FeedParser();
		ArrayList<News> newsList = null; //= // parser.parse(rootElement);

		ArrayList<CategoryNews> categoryNews = new ArrayList<CategoryNews>();

		CategoryNews catNews = new CategoryNews();
		// TODO now for testing purpose it is hard coded but must be remove it.
		catNews.setCategory("SportsNews");
		catNews.setNewsList(newsList);
		categoryNews.add(catNews);
		INewsDelegate delegate = (INewsDelegate)context;

		delegate.newsFetchedSuccess(categoryNews);
	}

	@Override
	public void onFailure(int serviceCode) {
		//need context for callback.
		if(context == null) return;
		if(context instanceof INewsDelegate) {
			INewsDelegate delegate = (INewsDelegate)context;
			delegate.newsFetchedFail("Error in fetching data");
		}

	}
	@Override
	public void onBeforeRequest(Element rootElement, int serviceCode) {
		// TODO Auto-generated method stub

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
