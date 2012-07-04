/**
 * 
 */
package com.daa.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import com.daa.util.Utility;

import android.graphics.Bitmap;
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
	
	public String getImage(String url) {
		String response = downloadFile(url);
		String imageUrl = getImageUrl(response);
		String strImageBitMap = downloadFile(imageUrl);
		Bitmap imgBitMap = Utility.getBitMap(strImageBitMap);
		String parse = parseResponse(strImageBitMap);
		return parse;
	}
	
	public String parseResponse(String response) {
		//String imageUrl = getImageUrl(response);
		  
		return null;
	}
	 
	
	private String getImageBitMap(String fileUrl) {
		if(fileUrl == null ) return null;
		URL url;
		try {
			url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.getOutputStream();
			
			Map<String, List<String>> headerMap = conn.getHeaderFields();
			 
				return null;
//			}
		} catch (IOException e) {
			Log.d("downloadFile"," an Error occured:: " + e.getMessage());
			return null;
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
			
			
//			String[] subSplit = split[1].split("href=");
//			String rightStringh = subSplit[1];
//			String[] subSplitUrl = rightStringh.split("/>");
//			String subSplitUrlImg = subSplitUrl[0];
			return url;
		} else {
			return null;
		}
	}
	 

}
