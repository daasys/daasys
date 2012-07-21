package com.daa.feedparser;

import android.graphics.Bitmap;

public class MediaThumbnail {
	private final android.net.Uri url;
	private final int height;
	private final int width;
	private final Bitmap bitmap;

	/**
	 * Returns the URL of the thumbnail.
	 * The return value is never {@code null}.
	 */
	public android.net.Uri getUrl() {
		return url;
	}

	/**
	 * Returns the thumbnail's height or {@code -1} if unspecified.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns the thumbnail's width or {@code -1} if unspecified.
	 */
	public int getWidth() {
		return width;
	}

	/* Internal constructor for RSSHandler */
	public MediaThumbnail(android.net.Uri url, int height, int width) {
		this.url = url;
		this.height = height;
		this.width = width;
		if(url != null) {
			bitmap = null;//loadBitmap(url.toString());	
		} else {
			bitmap = null;
		}
		
	}

	/**
	 * Returns the thumbnail's URL as a string.
	 */
	public String toString() {
		return url.toString();
	}

	/**
	 * Returns the hash code of the thumbnail's URL.
	 */
	@Override
	public int hashCode() {
		return url.hashCode();
	}

	/**
	 * Compares the URLs of two thumbnails for equality.
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object instanceof MediaThumbnail) {
			final MediaThumbnail other = (MediaThumbnail) (object);

			/* other is not null */
			return url.equals(other.url);
		} else {
			return false;
		}
	}
	
	 
	
//	public Bitmap loadBitmap(String url)
//	{
//	    Bitmap bm = null;
//	    InputStream is = null;
//	    BufferedInputStream bis = null;
//	    try 
//	    {
//	        URLConnection conn = new URL(url).openConnection();
//	        conn.connect();
//	        is = conn.getInputStream();
//	        bis = new BufferedInputStream(is, 8192);
//	        bm = BitmapFactory.decodeStream(bis);
//	    }
//	    catch (Exception e) 
//	    {
//	        e.printStackTrace();
//	    }
//	    finally {
//	        if (bis != null) 
//	        {
//	            try 
//	            {
//	                bis.close();
//	            }
//	            catch (IOException e) 
//	            {
//	                e.printStackTrace();
//	            }
//	        }
//	        if (is != null) 
//	        {
//	            try 
//	            {
//	                is.close();
//	            }
//	            catch (IOException e) 
//	            {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	    return bm;
//	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}

}
