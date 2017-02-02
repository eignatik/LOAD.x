package com.ngload.application.appcore.webcore;

import com.ngload.application.FakeServer;
import com.ngload.application.HTMLGetter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class WebConnectorTest {
    public static final Logger logger = LogManager.getLogger(WebConnectorTest.class.getName());

    private WebConnector connector;

    @BeforeTest
    public void createTestConnection() {
        FakeServer.runServer();
        connector = new WebConnector("http://localhost:8082");
    }

    @DataProvider
    public Object[][] prepareEndPointsForHTML() {
        return new Object[][]{
                {"/test"},
                {"test"},
                {"http://localhost:8082/test"}
        };
    }


    @Test(dataProvider = "prepareEndPointsForHTML")
    public void getHTMLByURLTest(String url) {
        String html = connector.getHtmlByURL(url);
        assertEquals(html, HTMLGetter.getBasicHTML());
    }

    //TODO: check need it  or not
//    @Test(dataProvider = "prepareBaseURLS")
//    public void addEndSlashTest(String URL, String expected) {
//        connector.setWorkURL(URL);
//        assertEquals(connector.getWorkURL(), expected);
//    }
}
