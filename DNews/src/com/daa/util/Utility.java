package com.daa.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
	 * Convert String to Bitmap.
	 * @param nodeValue
	 * @return
	 */
	public static Bitmap getBitMap(String nodeValue) {
		if(nodeValue == null) { return null; }
		//String strImage = readFile(path);

		try {
			byte[] byteArray = Base64.decode(nodeValue, Base64.DEFAULT);
			Bitmap bitMap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			return getResizedBitmap(bitMap, 24, 24);
		} catch (Exception e) {
			Log.e(TAG, "getBitMap() Error occured " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Resize the bitmap into specified width and height. 
	 * @param bm
	 * @param newHeight
	 * @param newWidth
	 * @return
	 */
	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		if(bm == null) {return null;}

		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// create a matrix for the manipulation
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);

		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

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
