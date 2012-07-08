 
package com.daa.feedparser;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * HTTP client to retrieve and parse RSS 2.0 feeds.
 *
 */
public class RSSReader implements java.io.Closeable {
	private final HttpClient httpclient;
	private final RSSParserSPI parser;

	public RSSReader() {
		this(new DefaultHttpClient(), new RSSParser(new RSSConfig()));

	}
	public RSSReader(HttpClient httpclient, RSSParserSPI parser) {
		this.httpclient = httpclient;
		this.parser = parser;
	}

	public RSSFeed load(String uri) throws RSSReaderException {
		final HttpGet httpget = new HttpGet(uri);

		InputStream feedStream = null;
		try {
			// Send GET request to URI
			final HttpResponse response = httpclient.execute(httpget);

			// Check if server response is valid
			final StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HttpStatus.SC_OK) {
				throw new RSSReaderException(status.getStatusCode(),
						status.getReasonPhrase());
			}

			// Extract content stream from HTTP response
			HttpEntity entity = response.getEntity();
			feedStream = entity.getContent();

			RSSFeed feed = parser.parse(feedStream);

			if (feed.getLink() == null) {
				feed.setLink(android.net.Uri.parse(uri));
			}

			return feed;
		} catch (ClientProtocolException e) {
			throw new RSSFault(e);
		} catch (IOException e) {
			throw new RSSFault(e);
		} finally {
			//Resources.closeQuietly(feedStream);
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
