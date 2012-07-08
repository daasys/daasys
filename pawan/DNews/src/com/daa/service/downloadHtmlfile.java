/**
 * 
 */
package com.daa.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * @author DaaSys
 *
 */
public class downloadHtmlfile {

	private String downloadFile(String url) {
		HttpResponse response;
		String responseString;
		HttpGet httpGet = null;

		HttpClient httpclient = WebClientDevWrapper.getHttpClient();        
		httpGet = new HttpGet(url.trim());
		try {
			response = httpclient.execute(httpGet);
			// check the response.
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				return responseString;
			} else {
				//Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Bitmap getImage(String url) {
		String response = downloadFile(url);
		String imageUrl = getImageUrl(response);
		//String strImageBitMap = 
		Bitmap imgBitMap = getImageBitMap(imageUrl);
		return imgBitMap;
	}

	 

	public String parseResponse(String response) {
		//String imageUrl = getImageUrl(response);

		return null;
	}


	private Bitmap getImageBitMap(String fileUrl) {
		if(fileUrl == null ) return null;
		URL url;
		try {
			url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.getOutputStream();
			writeToFile("test.png" , conn);
			String path = Environment.getExternalStorageDirectory() + "/" + "test.png";
			Bitmap imgBitMap = BitmapFactory.decodeFile(path);
			//Map<String, List<String>> headerMap = conn.getHeaderFields();

			return imgBitMap;
			//			}
		} catch (IOException e) {
			Log.d("downloadFile"," an Error occured:: " + e.getMessage());
			return null;
		}
	}
	
	private void writeToFile(String displayName, HttpURLConnection conn) {

		try {
			OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + displayName);
			InputStream myInputStream =null;
			myInputStream = conn.getInputStream();
			int lenghtOfFile = conn.getContentLength();
			byte data[] = new byte[1024];
			int count ;
			long total = 0;
			while ((count = myInputStream.read(data)) != -1) {
				total += count;
				//publishProgress((int)((total*100)/lenghtOfFile));
				output.write(data, 0, count);
			}

		} catch (FileNotFoundException e) {
			Log.e("tag", "writeToFile()" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getImageUrl(String responseString) {
		// 
		if(responseString != null && responseString.contains("image_src")) {
			String[] split = responseString.split("image_src");
			String strTosplit = split[1];
			int startIndex = strTosplit.indexOf("http");
			int endIndex =  strTosplit.indexOf(" />")-1;
			String url =  strTosplit.subSequence(startIndex, endIndex).toString();
			return url;
		} else {
			return null;
		}
	}


}
