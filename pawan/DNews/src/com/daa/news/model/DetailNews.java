package com.daa.news.model;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.widget.Toast;

import com.daa.news.delegate.IServiceDelegate;
import com.daa.news.service.DaaService;
import com.daa.news.views.DetailNewsActivity;

public class DetailNews implements IServiceDelegate {

	private static final int FETCH_NEWS = 200;
	private DetailNewsActivity activity;

	public void getDetailNews(String url, DetailNewsActivity activity) {
		DaaService service = new DaaService();
		service.setDelegate(this);
		this.activity = activity;
		service.tag = FETCH_NEWS;
		// send web service request.
		service.getDetailsNews(url);
	} 

	@Override
	public void onSuccess(Element rootElement, int serviceCode) {
		if(rootElement == null) {
			Toast.makeText(activity, "Unable to news", Toast.LENGTH_LONG).show();
			return;
		}
		NodeList allParam = rootElement.getElementsByTagName("p");
		if(allParam == null ) return ;
		int count = allParam.getLength();
		StringBuilder builder = new StringBuilder();
		for (int index = 0; index < count; index++) {
			Node node = allParam.item(index);
			String para = node.getFirstChild().getNodeValue();
			builder.append(para);
		}
		activity.newsFetchedFail(builder.toString());
	}

	@Override
	public void onFailure(int serviceCode) {


	}

	@Override
	public void onBeforeRequest(Element rootElement, int serviceCode) {


	}

}
