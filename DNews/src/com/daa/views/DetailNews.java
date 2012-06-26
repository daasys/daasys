package com.daa.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daa.R;
import com.daa.model.News;
import com.daa.util.DataManager;

public class DetailNews extends Activity {
	TextView txtNews;
	ImageView imgViewNews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_news);
		initViews();
	}
	/**
	 * Initialize views
	 */
	private void initViews() {
		txtNews = (TextView)findViewById(R.id.TextViewNews);
		//showNews();
		News news = DataManager.selectedNews;
		String description = "";
		if(news != null) {
			description = news.getDescription();	
		}
		final String testContent = "<html><body>"
				+ "<img src=\"icon.png\"/>This"+description+" </body></html>";
		txtNews.setText(Html.fromHtml(testContent, imgGetter, null));

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


}
