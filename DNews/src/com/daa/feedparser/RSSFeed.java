package com.daa.feedparser;

public class RSSFeed extends RSSBase {
	private final java.util.List<RSSItem> items;

	  RSSFeed() {
	    super(/* initial capacity for category names */ (byte) 3);
	    items = new java.util.LinkedList<RSSItem>();
	  }

	  /**
	   * Returns an unmodifiable list of RSS items.
	   */
	  public java.util.List<RSSItem> getItems() {
	    return java.util.Collections.unmodifiableList(items);
	  }

	  void addItem(RSSItem item) {
	    items.add(item);
	  }

}
