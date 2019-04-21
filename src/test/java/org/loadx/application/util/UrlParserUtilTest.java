package org.loadx.application.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UrlParserUtilTest {

    @DataProvider(name = "correctUrls")
    public Object[][] getCorrectUrls() {
        return new Object[][] {
                {"http://testurl:9999"},
                {"testurl:9999"},
                {"http://testurl:9999/"},
        };
    }

    @Test(dataProvider = "correctUrls")
    public void testGetPortReturnsPortWhenUrlIsCorrect(String url) {
        int port = UrlParserUtil.getPort(url);
        Assert.assertEquals(port, 9999);
    }

    @DataProvider(name = "wrongUrls")
    public Object[][] getWrongUrls() {
        return new Object[][] {
                {"http://testurl:9999:wrongthing"},
                {"testurl"},
                {""},
                {null}
        };
    }

    @Test(dataProvider = "wrongUrls", expectedExceptions = IllegalArgumentException.class)
    public void testGetPortThrowsExceptionWhenUrlIsWrong(String url) {
        UrlParserUtil.getPort(url);
    }

    @Test(dataProvider = "correctUrls")
    public void testHasPortReturnsTrueWhenUrlIsSuitable(String url) {
        Assert.assertTrue(UrlParserUtil.hasPort(url), "Port should be present in passed url");
    }

}