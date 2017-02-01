package com.ngload.application.appcore.webcore;

import com.ngload.application.FakeServer;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class ParserTest {
    private WebConnector connector;

    @BeforeSuite
    public void createTestConnection() {
        FakeServer.runServer();
        WebHelper.setWorkURL("http://localhost:8082/");
        connector = new WebConnector(WebHelper.getWorkURL());
    }

    @Test
    public void getLinksFromURLTest() {
        Assert.assertTrue(Parser.getLinksFromURL("testLinks").size()==4);
    }

    @Test
    public void getLotOfLinksFromURL() {
        Assert.assertTrue(Parser.getLinksFromURL("alotoflinksTest").size()==41);
    }
}
