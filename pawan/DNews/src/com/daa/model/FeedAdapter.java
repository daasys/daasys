package com.daa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daa.R;
import com.daa.feedparser.DrawableManager;
import com.daa.feedparser.MediaThumbnail;
import com.daa.feedparser.RSSItem;
import com.daa.util.DataManager;
import com.daa.views.DetailNews;

public class FeedAdapter extends BaseAdapter {
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;
	private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;
	
	private ArrayList mData = new ArrayList();
	private LayoutInflater mInflater;
	private Context context;
	private DrawableManager drableManager = new DrawableManager();

	private TreeSet mSeparatorsSet = new TreeSet();

	List<RSSItem> feeds;

	public FeedAdapter(Context context,  List<RSSItem> feeds,String category) {
		this.context = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.feeds = feeds;

		if(feeds != null) {

			addSeparatorItem(category);
			addItem(feeds);
		}
	}

	public void addItem(final Object item) {
		mData.add(item);
		notifyDataSetChanged();
	}

	public void addSeparatorItem(final String item) {
		mData.add(item);
		mSeparatorsSet.add(mData.size() - 1);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public String getItem(int position) {
		if(mData.get(position) == null) {
			return "";
		}
		return mData.get(position).toString();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		System.out.println("getView " + position + " " + convertView + " type = " + type);
		if (convertView == null) {
			holder = new ViewHolder();
			switch (type) {
			case TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.list_row, null);
				holder.textView = (TextView)convertView.findViewById(R.id.TextViewName);
				//addNews(convertView, holder, position);
				Object obj = mData.get(position);
				if(obj instanceof List<?>) {
					List<RSSItem> newsList = (List<RSSItem>) mData.get(position);
					if(newsList != null) {
						int newCount = newsList.size();
						for (int index = 0; index < newCount; index++) {
							addNews(convertView, holder, index, newsList.get(index));
						}
					}
				}else {

				}

				break;
			case TYPE_SEPARATOR:
				convertView = mInflater.inflate(R.layout.section_view, null);
				holder.textView = (TextView)convertView;
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		// display category.
		//String category = mData.get(position);
		if(holder.textView != null ) {
			holder.textView.setText(mData.get(position).toString());	
		}
		return convertView;
	}

	/**
	 * Add news according to category.
	 * @param convertView
	 * @param holder
	 * @param position
	 * @param news
	 */
	private void addNews(View convertView, ViewHolder holder, int position, RSSItem news) {
		LinearLayout view = (LinearLayout)convertView.findViewById(R.id.LinearLayoutNews);
		View viewToAdd = mInflater.inflate(R.layout.news_view, null);

		holder.textView = (TextView)viewToAdd.findViewById(R.id.TextViewName);
		holder.imgBtnNews = (ImageView)viewToAdd.findViewById(R.id.ImageViewNews);

		MediaThumbnail tbn = news.getThumbnail();
		if(tbn != null) {
			String thumbnailUrl = tbn.getUrl().toString();
			// fetch images.
			drableManager.fetchDrawableOnThread(thumbnailUrl, holder.imgBtnNews);
		}

		holder.textView.setText(news.getTitle());

		// set on click listener
		holder.imgBtnNews.setOnClickListener(onClickListener);
		holder.textView.setOnClickListener(onClickListener);
		holder.imgBtnNews.setTag(news);
		 
		if(view != null) {
			view.addView(viewToAdd);
		}
	}

	/**
	 * This class hold reference of views
	 * and avoid inflater of views again and again.
	 * @author DaaSys
	 *
	 */
	public static class ViewHolder {
		public TextView textView;
		public ImageView imgBtnNews;
	}

	/**
	 * On click listener that respond when news is clicked.
	 */
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, DetailNews.class);
			context.startActivity(intent);
			DataManager.selectedNews = (News)v.getTag();

		}
	};
}



