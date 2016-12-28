package com.ngload.application.appcore.webcore;

import com.ngload.application.HTMLGetter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static spark.Spark.*;

public class WebConnectorTest {
    private WebConnector connector;
    private HTMLGetter htmlGetter = new HTMLGetter();

    @BeforeTest
    public void createTestConnection() {
        port(8082);
        get("/test", (req, res) -> htmlGetter.getBasicHTML());
        connector = new WebConnector("http://localhost:8082");
    }

    @Test
    public void getHtmlByURLSimpleCorrectPath() {
        String html = connector.getHtmlByURL("/test");
        assertEquals(html, htmlGetter.getBasicHTML());
    }

    @Test
    public void getHtmlByURLSimplePathWithoutSlash() {
        String html = connector.getHtmlByURL("test");
        assertEquals(html, htmlGetter.getBasicHTML());
    }

    @Test
    public void getHtmlByURLFullPath() {
        String html = connector.getHtmlByURL("http://localhost:8082/test");
        assertEquals(html, htmlGetter.getBasicHTML());
    }
}
