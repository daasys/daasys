package com.daa.model;

import java.util.ArrayList;

import com.daa.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SettingAdapter extends BaseAdapter {
	
	private ArrayList<Category> category;
	private Context context;
	private LayoutInflater inflater;
	
	public SettingAdapter(ArrayList<Category> categoryList) {
		this.category = categoryList;
	}
	public SettingAdapter(Context context) {
		this.context = context;
		this.category = new ArrayList<Category>();
	}
	
	public void addCategory(Category category) {
		if(category != null) {
			this.category.add(category);
			notifyDataSetChanged();
		}
	}

	@Override
	public int getCount() {
		 if(category != null) {
			 return category.size();
		 }
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return category.get(position);
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
			 convertView = inflater.inflate(R.layout.setting, null);
			 holder.txtViewCategory = (TextView) convertView.findViewById(R.id.TextViewCategory);
			 convertView.setTag(holder);
		 } else {
			 holder = (ViewHolder) convertView.getTag();
		 }
		 Category strCategory = category.get(position);
		 if(strCategory != null) {
			 String categoryName = strCategory.getCategoryName();
			 holder.txtViewCategory.setText(categoryName);
		 }
		return convertView;
	}
	public class ViewHolder {
		private TextView txtViewCategory;
	} 

}
