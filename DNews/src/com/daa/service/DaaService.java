/**
 * 
 */
package com.daa.service;

import android.graphics.Bitmap;
import android.util.Log;
import com.daa.delegate.IServiceDelegate;
import com.daa.util.AppConstants;


/**
 * This class communicate with web service.
 * All service request is done from this class.
 * @author DaaSys
 *
 */
public class DaaService {
	private static final String TAG = "DrsServices";
	private IServiceDelegate delegate;
	//private IDownloadTask downloaddelegate;
	public int tag;

	public boolean sendNewsRequest() {
		// retrieve base url from shared preferences.
		String baseUrl = AppConstants.baseUrl;
		StringBuilder serviceUrl = new StringBuilder(baseUrl);
		// construct service url.
		serviceUrl.append("reuters/domesticNews");

		// prepare the service object and set the values.
		ServiceRequest  serviceRequest = new ServiceRequest();
		serviceRequest.setUrl(serviceUrl.toString());
		serviceRequest.setHTTPMethod(AppConstants.REQUEST_GET);
		serviceRequest.setDelegate(delegate);
		try {
			// make the request
			RequestTask fetchNewsRequest = new RequestTask();
			fetchNewsRequest.execute(serviceRequest);
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error occured::  " + e.getMessage());
			return false;
		}

	}

	public Bitmap fetchImage(String detailLink) {
		downloadHtmlfile file = new downloadHtmlfile();
		
		return file.getImage(detailLink); 
		// prepare the service object and set the values.
//		ServiceRequest  serviceRequest = new ServiceRequest();
//		serviceRequest.setUrl(detailLink);
//		serviceRequest.setHTTPMethod(AppConstants.REQUEST_GET);
//		serviceRequest.setDelegate(delegate);
//		try {
//			// make the request
//			RequestTask fetchNewsRequest = new RequestTask();
//			fetchNewsRequest.execute(serviceRequest);
//			return true;
//		} catch (Exception e) {
//			Log.e(TAG, "Error occured::  " + e.getMessage());
//			return false;
//		}

	}
	public IServiceDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(IServiceDelegate delegate) {
		this.delegate = delegate;
	}

}
