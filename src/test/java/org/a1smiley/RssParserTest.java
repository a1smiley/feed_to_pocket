package org.a1smiley;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RssParserTest {

  @Test
  void shouldCreateInstance() {
    RssParser test = new RssParser();
    assertNotNull(test);
  }

  @Test
  void shouldAddNewFeed() throws RssParserException {
    String feedUrl = "https://www.nasa.gov/rss/dyn/shuttle_station.rss";

    RssParser test = new RssParser();
    test.loadFeed(feedUrl);
    Set<String> loadedFeeds = test.getAllFeeds();
    Iterator<String> outputIterator = loadedFeeds.iterator();

    assertEquals(1, loadedFeeds.size());
    assertEquals(feedUrl, outputIterator.next());
    assertFalse(outputIterator.hasNext());
  }

  @Test
  void shouldThrowIOExceptionWhenBadUrl() {
    String badUrl = "http://www.test.com/badRss";
    RssParser test = new RssParser();
    assertThrows(RssParserException.class, () -> {
      test.loadFeed(badUrl);
    });
  }
}