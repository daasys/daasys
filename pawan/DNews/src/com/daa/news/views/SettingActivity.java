/**
 * 
 */
package com.daa.news.views;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daa.news.R;
import com.daa.news.model.SettingAdapter;
import com.daa.news.pref.DaaPrefs;

/**
 * @author DaaSys
 *
 */
public class SettingActivity extends ListActivity implements OnClickListener {

	private static final String TAG = "SettingActivity";
	// views
	private SettingAdapter cursorAdp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		initViews();
	}

	/**
	 * Initialise the views.
	 */
	private void initViews() {
		// Initialise header
		RelativeLayout header = (RelativeLayout) findViewById(R.id.header);
		TextView title = (TextView) header.findViewById(R.id.TextViewHeading);
		ImageView imageViewSave = (ImageView) header.findViewById(R.id.ImageViewRight);
		imageViewSave.setImageResource(R.drawable.ic_back);

		// set on click listener
		imageViewSave.setOnClickListener(this);

		title.setText(R.string.choose_category);
		initializeCategory();
	}

	private void initializeCategory() {
		try {
			DaaPrefs pref = DaaPrefs.getDaaPrefs(this);
			Map<String,Boolean> map = (Map<String, Boolean>) pref.getCategories();
			cursorAdp = new SettingAdapter(map, this);
			setListAdapter(cursorAdp);
			cursorAdp.notifyDataSetChanged();
		} catch (Exception e) {
			Log.e(TAG + "initializeCategory()", "An error:: " + e.getMessage());
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		setResult(Activity.RESULT_OK, intent);
		ArrayList<String> categories = cursorAdp.getSelectedCategory();
		categories.toArray();
		Bundle data = new Bundle();
		data.putStringArrayList("selected", categories);
		intent.putExtras(data);
		finish();
	}

}
