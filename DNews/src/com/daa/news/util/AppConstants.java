package com.daa.news.util;

/**
 * Contains all application global constants.
 * @author DaaSys
 *
 */
public class AppConstants {
	// Authentication.
	public static final boolean REQUEST_POST = true;
	public static final boolean REQUEST_GET = false;
	public static final String AUTHENTICATION_HEADER = "authorization";
	// base url for request to news web service
	public static final String baseUrl = "http://feeds.reuters.com/";//reuters/environment";
	public static final String UNKNOWN_ERROR_MESSAGE = "Server Error";

}
