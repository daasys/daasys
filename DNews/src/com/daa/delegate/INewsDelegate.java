/**
 * 
 */
package com.daa.delegate;

import java.util.ArrayList;

import com.daa.model.CategoryNews;

/**
 * Callback for fetching news.
 * @author DaaSys
 *
 */
public interface INewsDelegate {
	 void newsFetchedSuccess(ArrayList<CategoryNews> newsList);
	 void newsFetchedFail(String errorMsg);
}
