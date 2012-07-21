package com.daa.news.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daa.news.R;
import com.daa.feedparser.DrawableManager;
import com.daa.feedparser.MediaThumbnail;
import com.daa.feedparser.RSSItem;
import com.daa.news.delegate.INewsDelegate;
import com.daa.news.model.CategoryNews;
import com.daa.news.util.DataManager;

public class DetailNewsActivity extends Activity implements INewsDelegate  {

	private static final String TAG = "DetailNewsActivity";
	private TextView txtViewTitle;
	private TextView txtViewNews;
	private ImageView imgViewNews;
	private TextView txtViewPublishDate;
	private StringBuilder sb = null;
	private ViewGroup progressBar;

	// field variable
	private String title= null;

	private DrawableManager drableManager = new DrawableManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_news);
		initViews();
	}

	/**
	 * Initialise views
	 */
	private void initViews() {
		// views
		txtViewNews =  (TextView)findViewById(R.id.TextViewNews);
		txtViewTitle = (TextView)findViewById(R.id.TextViewTitle);
		txtViewPublishDate = (TextView)findViewById(R.id.TextViewPublishDate);
		imgViewNews = (ImageView)findViewById(R.id.ImageViewNews);
		progressBar = (ViewGroup)findViewById(R.id.progressbar);

		sb = new StringBuilder("<html><body>");
		showDetailNews();
		fetchOnHelperThread();

	}

	private void fetchOnHelperThread() {
		Thread t = new Thread() {
			public void run() {
				try {
					fetchStoryfronJsoup();
					handler.sendEmptyMessage(0);
				} catch (Exception e) {
					Log.e(TAG + "fetchOnHelperThread()", "ERROR :: " + e.getMessage());
					if(progressBar != null) {
						progressBar.setVisibility(View.GONE);
					}
				}
			};
		};
		t.start();
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			txtViewNews.append(Html.fromHtml(sb.toString()));
			txtViewTitle.setText(title);
			if(progressBar != null) {
				progressBar.setVisibility(View.GONE);
			}
		};
	};

	/**
	 * Display details news.
	 */
	private void showDetailNews() {
		try {
			RSSItem news = DataManager.selectedNews;
			if(news == null) return;

			String description = news.getDescription();
			title = news.getTitle();
			Date publishDate = news.getPubDate();

			txtViewNews.setText(description);
			txtViewTitle.setText(title);
			if(publishDate != null) {
				txtViewPublishDate.setText(publishDate.toLocaleString());
			}

			MediaThumbnail thumbs = news.getBigThumbnail();
			if(thumbs != null && thumbs.getUrl() != null) {
				Uri imageUri = thumbs.getUrl();
				drableManager.fetchDrawableOnThread(imageUri.toString(), imgViewNews);
			}
		} catch (Exception e) {
			Log.e(TAG + "showDetailNews()", "ERROR ::" + e.getMessage());
		}

	}

	private ImageGetter imgGetter = new ImageGetter() {

		public Drawable getDrawable(String source) {
			Drawable drawable = null;

			//			drawable = getResources().getDrawable(Integer.parseInt(DataManager.selectedNews.getUrl()));
			//			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
			//					.getIntrinsicHeight());
			return drawable;
		}
	};

	public void fetchStoryfronJsoup() {
		RSSItem item = DataManager.selectedNews;
		if(item == null || item.getLink() == null) return;
		String url = item.getLink().toString();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements para = doc.getElementsByTag("p");
			Elements titleEle = doc.getElementsByTag("title");
			if(titleEle != null && titleEle.text() != null) {
				title = titleEle.text();
				int startIndex = title.indexOf("-");
				String temp = title.substring(startIndex);
				title = temp;
			}

			int count = para.size();

			for (int index = 0; index < count; index++) {
				Element ele = para.get(index);
				String text = ele.text();
				if(text.contains("javaScript")) continue;
				if(text.contains("The BBC is not responsible for the content")) break;
				sb.append("<p> ");
				sb.append(text);
				sb.append(" </p>");
			}
			sb.append("</body></html>");
			//txtViewNews.setText(Html.fromHtml(testContent, imgGetter, null));
		} catch (IOException e) {
			Log.e(TAG + "fetchStoryfronJsoup() ", "ERROR ::" + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void newsFetchedSuccess(ArrayList<CategoryNews> newsList) {


	}
	@Override
	public void newsFetchedFail(String errorMsg) {
		txtViewNews.setText(errorMsg);

	}
}
