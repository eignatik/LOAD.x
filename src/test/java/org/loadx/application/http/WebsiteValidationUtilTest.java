package org.loadx.application.http;

import org.testng.Assert;
import org.testng.annotations.Test;

public class WebsiteValidationUtilTest {

    @Test
    public void testGenerateHashCreatesSequence() {
        String result = WebsiteValidationUtil.generateHash("mywebsite.com");
        Assert.assertEquals(result, "cdc68a4f71cc0f83db975f12944d2592", "MD5 hashes don't match");
    }

}