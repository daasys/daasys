package com.daa.feedparser;


public class RSSItem extends RSSBase {
	private final java.util.List<MediaThumbnail> thumbnails;
	private String content;

	/* Internal constructor for RSSHandler */
	RSSItem(byte categoryCapacity, byte thumbnailCapacity) {
		super(categoryCapacity);
		thumbnails = new java.util.ArrayList<MediaThumbnail>(thumbnailCapacity);
	}

	/* Internal method for RSSHandler */
	void addThumbnail(MediaThumbnail thumbnail) {
		thumbnails.add(thumbnail);
	}

	/**
	 * Returns an unmodifiable list of thumbnails. The return value is never
	 * {@code null}. Images are in order of importance.
	 */
	public java.util.List<MediaThumbnail> getThumbnails() {
		return java.util.Collections.unmodifiableList(thumbnails);
	}
	public MediaThumbnail getThumbnail() {
		if(thumbnails == null || thumbnails.size() == 0) return null;
		return thumbnails.get(0);
	}
	
	public MediaThumbnail getBigThumbnail() {
		if(thumbnails == null || thumbnails.size() == 0) return null;
		if(thumbnails.size() == 1) {
			return thumbnails.get(0);
		} else {
			return thumbnails.get(1);
		}
		
	}

	/**
	 * Returns the value of the optional &lt;content:encoded&gt; tag
	 * @return string value of the element data
	 */
	public String getContent() {
		return content;
	}

	/* Internal method for RSSHandler */
	void setContent(String content) {
		this.content = content;
	}

}
