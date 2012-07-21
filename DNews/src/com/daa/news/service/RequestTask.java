/**
 * 
 */
package com.daa.news.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.w3c.dom.Element;

import com.daa.news.util.AppConstants;

import android.os.AsyncTask;
import android.util.Log;

/**
 * This class Handle all network operation.
 * This works assyncrouse so there is no need to create separate worker thread.
 * @author DaaSys
 *
 */

class RequestTask extends AsyncTask<ServiceRequest, String, String> {

	private ServiceRequest serviceRequest;
	@Override
	protected String doInBackground(ServiceRequest... serviceRequests) {
		// get the HTTP Client for the self - signed certificate.
		HttpClient httpclient = WebClientDevWrapper.getHttpClient();        
		HttpResponse response;
		String responseString = null;
		HttpPost httpPost = null;
		HttpGet httpGet = null;
		try { 
			serviceRequest = serviceRequests[0];
			if(serviceRequest == null) return null;

			String url = serviceRequest.getUrl();

			Hashtable<String, String> httpHeaders = serviceRequest.getHTTPHeaders();

			if(serviceRequest.getHTTPMethod() == AppConstants.REQUEST_POST) {        	
				// post 
				httpPost = new HttpPost(url.trim());	
				addRequestHeaders(httpPost, httpHeaders);

				if(serviceRequest.getAdditionalHTTPBody() != null) {
					StringEntity stringEntity = new StringEntity(serviceRequest.getAdditionalHTTPBody());
					stringEntity.setContentType(serviceRequest.getContentType());
					httpPost.setEntity(stringEntity);
				}

				response = httpclient.execute(httpPost);
			} else {
				httpGet = new HttpGet(url.trim());
				addRequestHeaders(httpGet, httpHeaders);
				response = httpclient.execute(httpGet);
			}  

			// check the response.
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			} else {
				//Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}

		} catch (ClientProtocolException e) {
			Log.e("RequestTask" + "ClientProtocolException : ", "Error " + e.getMessage());
			return null;

		} catch (IOException e) {
			Log.e("RequestTask" + "IOException : ", "Error " + e.getMessage());
			return null;
		} catch (Exception e) {
			Log.e("RequestTask" + "Exception : ", "Error " + e.getMessage());
			return null;
		}
		return responseString;
	}


	@Override
	protected void onPostExecute(String result) {
		Element response = ServiceParser.getDocumentElement(result); 
		
		if(response == null && serviceRequest.getDelegate() != null) {
			serviceRequest.getDelegate().onFailure(serviceRequest.tag);
		} else if(serviceRequest.getDelegate() != null) {
			serviceRequest.getDelegate().onSuccess(response, serviceRequest.tag);
		}			
	}

	


	private void addRequestHeaders(HttpPost httpPost, Hashtable<String,String> httpHeaders) {
		// add the headers
		if(httpHeaders != null ) {                			
			Enumeration<String> allHeaders = httpHeaders.keys();
			while(allHeaders.hasMoreElements()) {
				String key = allHeaders.nextElement().trim();
				String value = httpHeaders.get(key).trim();
				httpPost.addHeader(key,value); 
			}        			        			
		}      		
	}

	private void addRequestHeaders(HttpGet httpGet, Hashtable<String,String> httpHeaders) {
		// add the headers
		if(httpHeaders != null ) {                			
			Enumeration<String> allHeaders = httpHeaders.keys();
			while(allHeaders.hasMoreElements()) {
				String key = allHeaders.nextElement().trim();
				String value = httpHeaders.get(key).trim();
				httpGet.addHeader(key,value);        				
			}        			        			
		}      		
	}
}