package org.a1smiley;

import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basic function is to maintain a list of RSS URLs, parse the feeds, identify articles
 * that are new since the last parse, and to provide a list of URLs for the new articles
 */
public class RssParser {
  private final SyndFeedInput input = new SyndFeedInput();
  private final Map<String, ZonedDateTime> lastArticleTime = new HashMap<>();

  public RssParser() {

  }

  public void loadFeed(String feedUrl) throws RssParserException {
    try {
      URL url = new URL(feedUrl);
      new XmlReader(url); // Create XmlReader to validate RSS feed url is well-formed
      lastArticleTime.put(feedUrl, ZonedDateTime.now());
    } catch (IOException ex) {
      throw new RssParserException(ex.getMessage());
    }
  }

  public Set<String> getAllFeeds() {
    return lastArticleTime.keySet();
  }
}
