package com.daa.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * Utility methods used through out application.
 * @author DaaSys
 *
 */
public class Utility {

	private static final String TAG = "Utility";

	/**
	 * Resize the image by fixed width and height.
	 * @param image
	 * @return
	 */
	public  static  Drawable getThumbnail(Drawable image) {
		try {
			Bitmap d = ((BitmapDrawable)image).getBitmap();
			Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, 64, 64, false);
			return new BitmapDrawable(bitmapOrig);
		} catch (Exception e) {
			Log.e(TAG, "Error occured" + e.getMessage());
			return null;
		}
		
	}

	/**
	 * Resize the image by specified width and height.
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public  static  Drawable getThumbnail(Drawable image, int width, int height) {
		try {
			Bitmap d = ((BitmapDrawable)image).getBitmap();
			Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, width, height, false);
			return new BitmapDrawable(bitmapOrig);
		} catch (Exception e) {
			Log.e(TAG, "Error occured" + e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * Convert string to inputStream.
	 * @param text
	 * @return
	 */
	public static InputStream stringToInputStream(String text) {
		try {
			return new ByteArrayInputStream(text.getBytes("UTF-8"));
		} catch(UnsupportedEncodingException e) {
			Log.e(TAG, "Error occured" + e.getMessage());
			return null;
		} catch(Exception e) {
			Log.e(TAG, "Error occured" + e.getMessage());
			return null;
		}
	}
}
