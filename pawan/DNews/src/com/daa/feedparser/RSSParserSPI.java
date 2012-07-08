package com.daa.feedparser;


/**
 * Thread-safe RSS parser service provider interface.
 * 
 */
public interface RSSParserSPI {

  /**
   * Parses an input stream as an RSS feed. It is the responsibility of the
   * caller to close the specified RSS feed input stream.
   * 
   * @param feed RSS 2.0 feed input stream
   * @return in-memory representation of RSS feed
   * @throws RSSFault if an unrecoverable parse error occurs
   */
  RSSFeed parse(java.io.InputStream feed);

}
