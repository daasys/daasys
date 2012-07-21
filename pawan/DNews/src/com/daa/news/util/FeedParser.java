package com.daa.news.util;


/**
 * Parse the xml response.
 * @author DaaSys
 *
 */
public class FeedParser {
//	private static final String TAG = "FeedParser";
//
//	public ArrayList<News> parse(Element root) {
//		ArrayList<News> newsList = new ArrayList<News>();
//		try {
//			if(root == null) return null;
//			NodeList ndeList = root.getElementsByTagName("item");
//			int size = ndeList.getLength();
//			for (int index = 0; index < size; index++) {
//
//				Node node = ndeList.item(index);
//				NodeList childNode = node.getChildNodes();
//				News news = getChildNodes(childNode);
//				if(news != null) {
//					newsList.add(news);	
//				}
//			}
//			return newsList;
//		} catch (Exception e) {
//			Log.e("FeedParser", "Error" + e.getMessage());
//			return null;
//		}
//
//	}
//	
//	/**
//	 * Fetch news feeds.
//	 * @param feedUrl
//	 * @return
//	 */
//	public ArrayList<News> parse(String feedUrl) {
//		ArrayList<News> newsList = new ArrayList<News>();
//		try {
//			//XMLElement  e; 
////			if(root == null) return null;
////			NodeList ndeList = root.getElementsByTagName("item");
////			int size = ndeList.getLength();
////			for (int index = 0; index < size; index++) {
////
////				Node node = ndeList.item(index);
////				NodeList childNode = node.getChildNodes();
////				News news = getChildNodes(childNode);
////				if(news != null) {
////					newsList.add(news);	
////				}
////			}
//			return newsList;
//		} catch (Exception e) {
//			Log.e("FeedParser", "Error" + e.getMessage());
//			return null;
//		}
//
//	}
//
//	/**
//	 * Create news according to xml tag.
//	 * @param childNode
//	 * @return
//	 */
//	private News getChildNodes(NodeList childNode) {
//		int length = childNode.getLength();
//		News  news = new News();
//		try {
//			for (int index = 0; index < length; index++) {
//				Node property = childNode.item(index);
//				String propertyName = property.getNodeName();
//				if(propertyName.equalsIgnoreCase("TITLE")) {
//					news.setTitle(property.getFirstChild().getNodeValue());
//				} else if(propertyName.equalsIgnoreCase("LINK")) {
//					news.setUrl(property.getFirstChild().getNodeValue());
//				} else if(propertyName.equalsIgnoreCase("DESCRIPTION")) {
//					news.setDescription(property.getFirstChild().getNodeValue());
//				} else if(propertyName.equalsIgnoreCase("guid")) {
//					news.setLink(property.getFirstChild().getNodeValue());
//					// fetch image bitmap.
//					getImageBitMap(property.getFirstChild().getNodeValue() , news);
//				}  
//			}
//			return news;
//		} catch (Exception e) {
//			Log.e(TAG, "error occured :: getChildNodes()");
//			return null;
//		}
//	}
//	private void getImageBitMap(final String imageUrl,final News news) {
//		 Thread t = new Thread() {
//			 @Override
//			public void run() {
//				 downloadHtmlfile file = new downloadHtmlfile();
//					
//					Bitmap bitmap =  file.getImage(imageUrl);
//					Message msg = new Message();
//					Bundle bundle = new Bundle();
//					bundle.putParcelable("image", bitmap);
//					msg.setData(bundle);
//					news.setImgBitMap(bitmap);
//					//handler.sendMessage(msg);
//				super.run();
//			}
//		 };
//		 t.start();
//		 
//	}
//
//	 Handler handler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			 
//			super.handleMessage(msg);
//			if(msg.getData() != null) {
//				Bundle budle = msg.getData();
//				Bitmap bitmap = (Bitmap) budle.get("image");
//				//news.setImgBitMap(bitmap);
//			}
//			
//		} 
//	 };
	
}
