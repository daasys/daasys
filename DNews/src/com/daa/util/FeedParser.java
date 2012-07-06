package com.daa.util;

import java.util.ArrayList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.util.Log;
import com.daa.model.News;

/**
 * Parse the xml response.
 * @author DaaSys
 *
 */
public class FeedParser {

	public ArrayList<News> parse(Element root) {
		ArrayList<News> newsList = new ArrayList<News>();
		try {
			if(root == null) return null;
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

	/**
	 * Create news according to xml tag.
	 * @param childNode
	 * @return
	 */
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
				} else if(propertyName.equalsIgnoreCase("guid")) {
					news.setLink(property.getFirstChild().getNodeValue());
				}  
			}
			return news;
		} catch (Exception e) {
			return null;
		}
	}
	public void  getImage() {
		Thread thread = new Thread();
		
	}
	
}
