package org.loadx.application.http;

import org.loadx.application.exceptions.LoadxException;
import org.loadx.application.util.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class WebsiteValidationUtilTest {

    private static final String ADDRESS = "mywebsite.com";
    private static final String TEST_FILE_PATH = "./src/test/resources/websiteValidation/pageForValidation.html";

    @Test
    public void testGenerateHashCreatesSequence() {
        String expectedMd5Hash = "cdc68a4f71cc0f83db975f12944d2592";
        String result = WebsiteValidationUtil.generateHash(ADDRESS);
        Assert.assertEquals(result, expectedMd5Hash, "MD5 hashes don't match");
    }

    @Test
    public void testValidateShouldReturnTrueWhenHashesAreTheSame() {
        String page = FileUtils.extractResourceToString(TEST_FILE_PATH);
        String generatedHash = WebsiteValidationUtil.generateHash(ADDRESS);
        assertTrue(WebsiteValidationUtil.validate(generatedHash, page), "The validation failed, but shouldn't have");
    }

    @Test
    public void testValidateShouldReturnFalseWhenHashesAreDifferent() {
        String page = FileUtils.extractResourceToString(TEST_FILE_PATH);
        String hash = "cd56cd6s6c87sd78c8csduc723";
        assertFalse(WebsiteValidationUtil.validate(hash, page), "The validation passed, but shouldn't have");
    }

    @Test(expectedExceptions = LoadxException.class)
    public void testValidateShouldThrowAnExceptionWhenPassedHashIsEmpty() {
        String emptyHash = "";
        WebsiteValidationUtil.validate(emptyHash, "dummyPageContent42");
    }

}