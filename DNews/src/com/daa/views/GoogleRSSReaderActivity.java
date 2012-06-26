package com.daa.views;
import java.util.ArrayList;
import java.util.TreeSet;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daa.R;
import com.daa.model.News;
import com.daa.util.DataManager;
import com.daa.util.FeedParser;

public class GoogleRSSReaderActivity extends ListActivity implements OnClickListener {

	private MyCustomAdapter mAdapter;
	ArrayList<News> newsList;
	//private String []category = {"US news", "Sports News","Technology","Entertainment"};
	//private String []news = {"Mursi to build Egypt government", "Syrian officers 'flee to Turkey","Europe crisis cuts aid to poor","Anger at Uganda Olympics mix-up ","Crices in India"};
	private static final Integer[] Icons = {
		R.drawable.new3,R.drawable.news2,R.drawable.news1,R.drawable.news1,R.drawable.new3};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getNews();
	}

	private void getNews() {
		FeedParser parser = new FeedParser();
		newsList =  parser.parse(this);
		if(newsList != null && newsList.size() > 0) {
			int noOfNews = newsList.size();
			mAdapter = new MyCustomAdapter();
			for (int index = 0; index < noOfNews; index++) {
				mAdapter.addItem(newsList.get(index).getTitle());
				//mAdapter.addSeparatorItem(category[index]);
			}
			setListAdapter(mAdapter);
		}

	}

	private class MyCustomAdapter extends BaseAdapter {

		private static final int TYPE_ITEM = 0;
		private static final int TYPE_SEPARATOR = 1;
		private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

		private ArrayList mData = new ArrayList();
		private LayoutInflater mInflater;

		private TreeSet mSeparatorsSet = new TreeSet();

		public MyCustomAdapter() {
			mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void addItem(final String item) {
			mData.add(item);
			notifyDataSetChanged();
		}

		public void addSeparatorItem(final String item) {
			mData.add(item);
			// save separator position
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
					// set on click listener of news image


					addNews(convertView, holder, position);
					if(position > 1) {
						for (int index = 0; index < 4; index++) {
							addNews(convertView, holder, position);
						}
					}
					break;
				case TYPE_SEPARATOR:
					convertView = mInflater.inflate(R.layout.section_view, null);
					holder.textView = (TextView)convertView;
					holder.textView.setOnClickListener(GoogleRSSReaderActivity.this);
					break;
				}
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			holder.textView.setText(mData.get(position).toString());
			holder.textView.setTag(position);
			holder.textView.setOnClickListener(GoogleRSSReaderActivity.this);
			return convertView;
		}

		private void addNews(View convertView, ViewHolder holder, int position) {
			LinearLayout view = (LinearLayout)convertView.findViewById(R.id.LinearLayoutNews);
			View viewToAdd = mInflater.inflate(R.layout.news_view, null);
			holder.textView = (TextView)viewToAdd.findViewById(R.id.TextViewName);
			holder.imgBtnNews = (ImageView)viewToAdd.findViewById(R.id.ImageViewNews);
			holder.imgBtnNews.setImageResource(Icons[position]);
			//holder.textView.setText(news[position]);
			holder.imgBtnNews.setOnClickListener(GoogleRSSReaderActivity.this);
			holder.imgBtnNews.setTag(position);

			if(view != null) {
				view.addView(viewToAdd);
			}
		}

	}

	public static class ViewHolder {
		public TextView textView;
		public ImageView imgBtnNews;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i("test", "position " + position);
		Toast.makeText(this, "U click on position:: " + position, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.TextViewName:
			Log.i("test", "position ");

			News selectedNews = newsList.get((Integer)v.getTag());
			selectedNews.setUrl(Icons[(Integer)v.getTag()].toString());
			if(selectedNews != null) {
				DataManager.selectedNews = selectedNews;
				showDetailsNews();
			}
			break;
		case R.id.ImageViewNews:
			News news = newsList.get((Integer)v.getTag());
			news.setUrl(Icons[(Integer)v.getTag()].toString());
			if(news != null) {
				DataManager.selectedNews = news;
				showDetailsNews();
			}
			break;
		default:
			break;
		}
	}

	private void showDetailsNews() {
		Intent intent = new Intent(this,DetailNews.class);
		startActivity(intent);
	}
}