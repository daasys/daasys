package com.daa.news.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
			Log.e(TAG + "getBitMap() ", "getBitMap() Error occured " + e.getMessage());
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
	public static Bitmap getThumbnail(Bitmap image) {
		if(image == null) return null;
		try {
			//Bitmap d = ((BitmapDrawable)image).getBitmap();
			Bitmap bitmapOrig = Bitmap.createScaledBitmap(image, 64, 48, false);
			return  bitmapOrig;
		} catch (Exception e) {
			Log.e(TAG + "getThumbnail()", "Error occured " + e.getMessage());
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
			Log.e(TAG + "getThumbnail() ", "Error occured" + e.getMessage());
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
			Log.e(TAG + "stringToInputStream()  ", "Error occured" + e.getMessage());
			return null;
		} catch(Exception e) {
			Log.e(TAG + "stringToInputStream()  ", "Error occured" + e.getMessage());
			return null;
		}
	}

	/**
	 * Check Internet connection availability.
	 * @param context
	 * @return 
	 */
	public static  boolean isNetworkAvailable(Context context) {
		if(context == null) { return false; }
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// if no network is available networkInfo will be null, otherwise check if we are connected
		try {
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			Log.e(TAG + "isNetworkAvailable()", "ERROR ::" + e.getMessage());
		}
		return false;
	}

	/**
	 * Show alert dialog.
	 * @param context
	 * @param message
	 */
	public static void showMSG(Context context, String message) {

		if(message == null || message.length() == 0) {
			message = AppConstants.UNKNOWN_ERROR_MESSAGE;
		}

		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setMessage(message);
		dialog.setButton("Ok",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				default:
					break;
				}
			}
		});
		dialog.show();
	}

}
