package com.daa.service;

import java.util.Hashtable;

import com.daa.delegate.IServiceDelegate;

/**
 * Configure web service request.
 * @author DaaSys
 *
 */
public class ServiceRequest {

	public int tag;
	private String url;
	private boolean httpMethod;
	private String contentType;
	private String additionalHTTPBody;
	private Hashtable<String, String>	requestParams;
	private Hashtable<String, String> additionalHTTPHeaders;	
	private IServiceDelegate delegate;
	public boolean canQueue;
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getAdditionalHTTPBody() {
		return additionalHTTPBody;
	}

	public void setAdditionalHTTPBody(String additionalHTTPBody) {
		this.additionalHTTPBody = additionalHTTPBody;
	}

	public ServiceRequest(String url, int tag) {
		this.url = url;
		this.tag = tag;
	}
	
	public ServiceRequest() {		
	}
	
	public IServiceDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(IServiceDelegate delegate) {
		this.delegate = delegate;
	}
	
	public boolean getHTTPMethod() {
		return httpMethod;
	}

	public void setHTTPMethod(boolean httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	public void addRequestParameter(String key , String value) {
		if(requestParams == null) {
			requestParams = new Hashtable<String, String>();
		} 		
		requestParams.put(key, value);
	}
	
	public void addHTTPHeader(String key , String value) {
		if(additionalHTTPHeaders == null) {
			additionalHTTPHeaders = new Hashtable<String, String>();
		} 		
		additionalHTTPHeaders.put(key, value);
	}
	
	public Hashtable<String , String> getHTTPHeaders() {
	     return additionalHTTPHeaders;	
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
		
	public void doRequest() {
		// TODO:  Need to add AsyncTask to send http request		
	}	
}
