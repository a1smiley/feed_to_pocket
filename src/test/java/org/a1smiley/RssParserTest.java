package org.a1smiley;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Iterator;
import java.util.Set;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

class RssParserTest {
  private static MockWebServer mockServer = new MockWebServer();

  @Test
  void shouldCreateInstance() {
    RssParser test = new RssParser();
    assertNotNull(test);
  }

  @Test
  void shouldAddNewFeed() throws RssParserException {
    mockServer.enqueue(new MockResponse()
        .addHeader("Content-Type", "application/xml; charset=utf-8")
        .setBody(RssParserTest.class.getClassLoader().getResource("sample_rss.xml").toString())
        .setResponseCode(200));

    String feedUrl = mockServer.url("/").toString();
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