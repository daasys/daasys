package com.daa.news.model;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daa.news.R;
import com.daa.news.pref.DaaPrefs;

public class SettingAdapter extends BaseAdapter {
	private static int ON = 100;
	private static int OFF = 101;
	private Map<String,Boolean> mapCategory;
	private Context context;
	private LayoutInflater inflater;

	Object[] allCAtegories;
	private ArrayList<String> selectedCategory = new ArrayList<String>();
	DaaPrefs pref;


	public ArrayList<String> getSelectedCategory() {
		return selectedCategory;
	}


	public SettingAdapter(Map<String, Boolean> categoryList,Context context) { 
		this.mapCategory = categoryList;
		this.context = context;
		allCAtegories = mapCategory.keySet().toArray();
		pref = DaaPrefs.getDaaPrefs(context);

	}


	public void addCategory(String key,Boolean isSelected) {
		if(mapCategory != null) {
			this.mapCategory.put(key, isSelected);
			notifyDataSetChanged();
		}
	}


	@Override
	public int getCount() {
		if(mapCategory != null) {
			return mapCategory.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {

		return allCAtegories[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.setting_row, null);
			holder.txtViewCategory = (TextView) convertView.findViewById(R.id.TextViewCategory);
			holder.txtViewCategory.setOnClickListener(onClick);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String strCategory = (String) allCAtegories[position];
		boolean isSelected = mapCategory.get(strCategory);
		holder.txtViewCategory.setText(strCategory);
		if(isSelected) {
			selectedCategory.add(strCategory);
			holder.txtViewCategory.setTag(ON);
			holder.txtViewCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background, 0);
		} else {
			holder.txtViewCategory.setTag(OFF);
			holder.txtViewCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_off_background, 0);
		}

		holder.txtViewCategory.setTag(R.string.app_name, position);
		return convertView;
	}

	private static class ViewHolder {
		private TextView txtViewCategory;
	} 

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			TextView textView = (TextView) v;
			int tag = (Integer) textView.getTag();
			int selectedPosition = (Integer) textView.getTag(R.string.app_name);
			String key = (String) allCAtegories[selectedPosition];
			boolean state;
			if(tag == ON) {
				if(selectedCategory.contains(key)) {
					selectedCategory.remove(key);
				}

				textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_off_background, 0);
				textView.setTag(OFF);
				state = false;
			} else {
				textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background, 0);
				textView.setTag(ON);
				state = true;
				if(!selectedCategory.contains(key)) {
					selectedCategory.add(key);
				}
			}

			pref.setSelectionState(key,state);
		}
	};
}


/**
 * 

 * private static int ON = 100;
	private static int OFF = 101;
	private ArrayList<Category> categoryList;
	private Context context;
	private LayoutInflater inflater;

	public SettingCursorAdapter(ArrayList<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public SettingCursorAdapter(Context context) {
		this.context = context;
		this.categoryList = new ArrayList<Category>();
	}

	public void addCategory(Category category) {
		if(category != null) {
			this.categoryList.add(category);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		if(categoryList != null) {
			return categoryList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return categoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.setting_row, null);
			holder.txtViewCategory = (TextView) convertView.findViewById(R.id.TextViewCategory);
			holder.txtViewCategory.setOnClickListener(onClick);
			holder.txtViewCategory.setTag(ON);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Category strCategory = categoryList.get(position);
		if(strCategory != null) {
			String categoryName = strCategory.getCategoryName();
			holder.txtViewCategory.setText(categoryName);
		}
		return convertView;
	}
	private static class ViewHolder {
		private TextView txtViewCategory;
	} 

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			TextView textView = (TextView) v;
			 int tag = (Integer) textView.getTag();
			 if(tag == ON) {
				 textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_off_background, 0);
				 textView.setTag(OFF);
			 } else {
				 textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.checkbox_on_background, 0);
				 textView.setTag(ON);
			 }
		}
	};



}
 */
