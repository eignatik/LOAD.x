package com.ngload.application.appcore.webcore;

import com.ngload.application.HTMLGetter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
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

    @DataProvider
    public Object[][] prepareEndPoints() {
        return new Object[][]{
                {"/test"},
                {"test"},
                {"http://localhost:8082/test"}
        };
    }

    @DataProvider
    public Object[][] prepareBaseURLS() {
        return new Object[][]{
                {"http://localhost:8802", "http://localhost:8802/"},
                {"http://localhost:8802/", "http://localhost:8802/"},
                {"http://localhost:8802//", "http://localhost:8802/"},
                {"http://localhost:8802///", "http://localhost:8802/"},
                {"http://test.ru/", "http://test.ru/"}
        };
    }

    @Test(dataProvider = "prepareEndPoints")
    public void getHTMLByURLTest(String url) {
        String html = connector.getHtmlByURL(url);
        assertEquals(html, htmlGetter.getBasicHTML());
    }

    @Test(dataProvider = "prepareBaseURLS")
    public void addEndSlashTest(String URL, String expected) {
        connector.setWorkURL(URL);
        assertEquals(connector.getWorkURL(), expected);
    }
}
