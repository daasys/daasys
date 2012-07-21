/**
 * 
 */
package com.daa.news.delegate;

import java.util.ArrayList;

import com.daa.news.model.CategoryNews;

/**
 * Callback for fetching news.
 * @author DaaSys
 *
 */
public interface INewsDelegate {
	 void newsFetchedSuccess(ArrayList<CategoryNews> newsList);
	 void newsFetchedFail(String errorMsg);
}
