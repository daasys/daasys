package com.daa.util;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.daa.model.News;

public class FeedParser {

	public ArrayList<News> parse(Context context) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			AssetManager assets = context.getAssets();
			InputStream stream = assets.open("News.xml");
			Document dom = builder.parse(stream);
			Element root = dom.getDocumentElement();
			NodeList ndeList = root.getElementsByTagName("item");
			int size = ndeList.getLength();
			for (int index = 0; index < size; index++) {

				Node node = ndeList.item(index);
				NodeList childNode = node.getChildNodes();
				News news = getChildNodes(childNode);
				if(news != null) {
					newsList.add(news);	
				}

			}
			return newsList;
		} catch (Exception e) {
			Log.e("FeedParser", "Error" + e.getMessage());
			return null;
		}

	}

	private News getChildNodes(NodeList childNode) {
		int length = childNode.getLength();
		News news = new News();
		try {
			for (int index = 0; index < length; index++) {
				Node property = childNode.item(index);
				String propertyName = property.getNodeName();
				if(propertyName.equalsIgnoreCase("TITLE")) {
					news.setTitle(property.getFirstChild().getNodeValue());
				} else if(propertyName.equalsIgnoreCase("LINK")) {
					news.setUrl(property.getFirstChild().getNodeValue());
				} else if(propertyName.equalsIgnoreCase("DESCRIPTION")) {
					news.setDescription(property.getFirstChild().getNodeValue());
				} else if(propertyName.equalsIgnoreCase("")) {

				}
			}
			return news;
		} catch (Exception e) {
			return null;
		}

	}

}
