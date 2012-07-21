/**
 * 
 */
package com.daa.news.pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author DaaSys
 *
 */
public class DaaPrefs {
	// prefs file name 
	private static final String PREFS_NAME = "daaPrefs";
	private static final String PREFS_NAME_SELECTED = "daaPrefsSelected";
	
	private static final String TECHNOLOGY = "Technology";
	private static final String WORLD = "World";
	private static final String EDUCATION = "Education";
	
	private static final String HEAD_LINES = "HeadLines";
	private static final String POLITICE = "Politics";
	private static final String HEALTHS = "Healths";
	
	private static final String world = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk";
	private static final String technology = "http://feeds.bbci.co.uk/news/technology/rss.xml?edition=uk";
	private static final String education = "http://feeds.bbci.co.uk/news/education/rss.xml?edition=uk";
	
//	private static final String headlines = "http://feeds.bbci.co.uk/news/video_and_audio/news_front_page/rss.xml?edition=uk";
	private static final String headlines = "http://feeds.bbci.co.uk/news/rss.xml?edition=uk";
	private static final String politics =	"http://feeds.bbci.co.uk/news/politics/rss.xml?edition=uk";
	private static final String healths = "http://feeds.bbci.co.uk/news/health/rss.xml?edition=uk";
	
	// ABC NEWS FEEDS
	//private static final String headlines = "http://feeds.abcnews.com/abcnews/internationalheadlines";
	 

	// prefs managers
	private SharedPreferences daaSharedPrefs;
	private SharedPreferences daaSharedPrefssELECTED;
	private Editor prefsEditor;
	private static DaaPrefs prefs;

	private DaaPrefs(Context context) {
		// Initialize the shared prefs
		daaSharedPrefs =  context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		prefsEditor = daaSharedPrefs.edit(); 
		initializeCategory();
		
		daaSharedPrefssELECTED = context.getSharedPreferences(PREFS_NAME_SELECTED, Context.MODE_PRIVATE);
		setSelectionState(WORLD, false);
		setSelectionState(TECHNOLOGY, false);
		setSelectionState(EDUCATION, false);
		
		setSelectionState(HEAD_LINES, true);
		setSelectionState(POLITICE, false);
		setSelectionState(HEALTHS, false);
		
	}
	
	public static DaaPrefs getDaaPrefs(Context context) {
		if(prefs == null) {
			prefs = new DaaPrefs(context);
		}  
		return prefs;
	}
	
	 
	
	public Map<String, ?> getAll() {
		return daaSharedPrefs.getAll();
	}

	/**
	 * Initialise the category.
	 */
	private void initializeCategory() {
		 prefsEditor.putString(WORLD, world);
		 prefsEditor.putString(TECHNOLOGY, technology);
		 prefsEditor.putString(EDUCATION, education);
		 
		 prefsEditor.putString(HEAD_LINES, headlines);
		 prefsEditor.putString(POLITICE, politics);
		 prefsEditor.putString(HEALTHS, healths);
		 prefsEditor.commit();
	}
	
	public void save(String key, Boolean value) {
		prefsEditor.putBoolean(key, value);
		prefsEditor.commit();
	} 
	
	public String getURLValue(String key) {
		return daaSharedPrefs.getString(key, null);
	}
	
	public void setSelectionState(String key, boolean state) {
		Editor edit = daaSharedPrefssELECTED.edit();
		edit.putBoolean(key,state);
		edit.commit();
	}
	
	public Map<String, ?> getCategories() {
		 return daaSharedPrefssELECTED.getAll();
	}
	
	public HashMap<String, String> getSelectedCategories(ArrayList<String> list) {
		if(list == null) return null;
		HashMap<String, String> map = new HashMap<String, String>();
		for (int index = 0; index < list.size(); index++) {
			String key = list.get(index);
			String value = daaSharedPrefs.getString(key, null);
			map.put(key, value);
		}
		return map;
	}
	
	public String getHeadLines() {
		return daaSharedPrefs.getString(HEAD_LINES, null);
	}
	 
}
