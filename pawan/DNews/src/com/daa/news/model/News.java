package com.daa.news.model;

import android.graphics.Bitmap;

/**
 * DaaSys * @author DaaSys
 *
 */
public class News {
	private String news;
	private String description;
	private String url;
	private String title;
	private String link;
	private Bitmap imgBitMap;
	
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Bitmap getImgBitMap() {
		return imgBitMap;
	}
	public void setImgBitMap(Bitmap imgBitMap) {
		this.imgBitMap = imgBitMap;
	}
	

}
