package com.ngload.application.appcore.webcore;

import com.ngload.application.HTMLGetter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import spark.Spark;

public class ParserTest {
    private WebConnector connector;
    private HTMLGetter htmlGetter = new HTMLGetter();

    @BeforeTest
    public void createTestConnection() {
        Spark.port(8802);
        Spark.get("/testLinks", (req, res) -> htmlGetter.getLinksHTML());
        Spark.get("/alotoflinksTest", (req, res) -> htmlGetter.getLotOfLinksHTML());
        WebHelper.setWorkURL("http://localhost:8802/");
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
