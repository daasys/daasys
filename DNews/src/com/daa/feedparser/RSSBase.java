package com.daa.feedparser;

import java.util.ArrayList;

public class RSSBase {
	private String title;
	private android.net.Uri link;
	private String description;
	private java.util.List<String> categories;
	private java.util.Date pubdate;

	/**
	 * Specify initial capacity for the List which contains the category names.
	 */
	RSSBase(byte categoryCapacity) {
		categories = categoryCapacity == 0 ? null : new ArrayList<String>(
				categoryCapacity);
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public android.net.Uri getLink() {
		return link;
	}

	public java.util.List<String> getCategories() {
		if (categories == null) {
			return java.util.Collections.emptyList();
		}

		return java.util.Collections.unmodifiableList(categories);
	}

	public java.util.Date getPubDate() {
		return pubdate;
	}

	void setTitle(String title) {
		this.title = title;
	}

	void setLink(android.net.Uri link) {
		this.link = link;
	}

	void setDescription(String description) {
		this.description = description;
	}

	void addCategory(String category) {
		if (categories == null) {
			categories = new ArrayList<String>(3);
		}

		this.categories.add(category);
	}

	void setPubDate(java.util.Date pubdate) {
		this.pubdate = pubdate;
	}

	/**
	 * Returns the title.
	 */
	 public String toString() {
		return title;
	}

	 /**
	  * Returns the hash code of the link.
	  */
	 @Override
	 public int hashCode() {
		 if (link == null) {
			 return 0;
		 }

		 return link.hashCode();
	 }

	 /**
	  * Compares the links for equality.
	  */
	 @Override
	 public boolean equals(Object object) {
		 if (this == object) {
			 return true;
		 } else if (object instanceof RSSBase) {
			 /* other is never null */
			 final RSSBase other = (RSSBase) (object);

			 if (link == null) {
				 return other.link == null;
			 }

			 return link.equals(other.link);
		 } else {
			 return false;
		 }
	 }

}

