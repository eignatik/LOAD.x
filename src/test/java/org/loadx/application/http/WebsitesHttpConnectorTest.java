package org.loadx.application.http;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebsitesHttpConnectorTest {

    @DataProvider(name = "correctUrls")
    public Object[][] provideCorrectUrls() {
        return new Object[][] {
                {"www.example.com"},
                {"http://example.com"},
                {"https://example.com"},
                {"http://www.example.com"},
                {"http://afew.levels.com"},
        };
    }

    @Test(dataProvider = "correctUrls")
    public void testBuilderReturnsConnector(String baseUrl) {
        WebsitesHttpConnector httpConnector = WebsitesHttpConnector.ConnectorBuilder.createDefault()
                .withBaseUrl(baseUrl)
                .build();

        Assert.assertNotNull(httpConnector, "Connector should be not null");
        Assert.assertEquals(httpConnector.getBaseUrl(), baseUrl, "Base URL should be like: " + baseUrl);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBuilderFailsValidationWhenNoBaseUrlSet() {
        WebsitesHttpConnector.ConnectorBuilder.createDefault().build();
    }

    @DataProvider(name = "incorrectUrls")
    public Object[][] provideIncorrectUrls() {
        return new Object[][] {
                {"incorrectSequnce"},
                {"withoutProtocols.com"},
                {"withoutProtocols.com"},
                {"http.incorrectProtocols.com"},
                {"htp//:incorrectProtocols.com"},
                {"AAhttps://prefixesNotGood.com"},
        };
    }

    @Test(dataProvider = "incorrectUrls", expectedExceptions = IllegalArgumentException.class)
    public void testBuilderFailsValidationWhenBaseUrlDoesNotMatchPatternOfUrl(String baseUrl) {
        WebsitesHttpConnector.ConnectorBuilder.createDefault()
                .withBaseUrl(baseUrl)
                .build();
    }

}