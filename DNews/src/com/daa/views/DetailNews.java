package com.daa.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html.ImageGetter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daa.R;
import com.daa.model.CategoryNews;
import com.daa.model.News;
import com.daa.util.DataManager;
import com.daa.util.Utility;

public class DetailNews extends Activity  {
	private TextView txtViewTitle;
	private TextView txtViewNews;
	private ImageView imgViewNews;

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
		imgViewNews = (ImageView)findViewById(R.id.ImageViewNews);
		showDetailNews();

	}

	/**
	 * Display details news.
	 */
	private void showDetailNews() {
		News news = DataManager.selectedNews;
		if(news == null) return;
		
		String description = news.getDescription();
		String title = news.getTitle();
		String detailLink = news.getLink();
		fetchImage(detailLink);
		// TODO This is hard coded image resource id.
		//Drawable drawable = getResources().getDrawable(Integer.parseInt(DataManager.selectedNews.getUrl()));
		// Set to widgets.
		txtViewNews.setText(description);
		txtViewTitle.setText(title);
		
		//Drawable resizeDrawable = Utility.getThumbnail(drawable, 128, 128);
		imgViewNews.setImageBitmap(DataManager.bitMap);
	}

	private void fetchImage(String detailLink) {
		 try {
			CategoryNews cateNews = new CategoryNews();
			cateNews.setContext(this);
			cateNews.fetchImage(detailLink);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private ImageGetter imgGetter = new ImageGetter() {

		public Drawable getDrawable(String source) {
			Drawable drawable = null;

			drawable = getResources().getDrawable(Integer.parseInt(DataManager.selectedNews.getUrl()));
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
					.getIntrinsicHeight());
			return drawable;
		}
	};
	
	private void fetchImageFromWebService(String url) {
		
	}
	
	/*
	 * final String testContent = "<html><body>"
				+ "<img src=\"icon.png\"/>This"+description+" </body></html>";
		txtNews.setText(Html.fromHtml(testContent, imgGetter, null));

	 */


}
