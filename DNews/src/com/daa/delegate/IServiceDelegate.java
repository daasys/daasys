package com.daa.delegate;

import org.w3c.dom.Element;

/**
 * Delegate/interface to handle service call backs.
 * @author DaaSys
 */
public interface IServiceDelegate {
	public void onSuccess(Element rootElement, int serviceCode);
	public void onFailure(int serviceCode);
	public void onBeforeRequest(Element rootElement, int serviceCode);
}
