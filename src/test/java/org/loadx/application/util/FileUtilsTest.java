package org.loadx.application.util;

import org.loadx.application.exceptions.LoadxException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class FileUtilsTest {

    @Test
    public void testExtractResourceToStringShouldReturnCorrectString() {
        String filePath = "./src/test/resources/simplePageForTest.html";
        String result = FileUtils.extractResourceToString(filePath);
        assertEquals(result, getExpectedContent(), "File has unexpected content");
    }

    @Test(expectedExceptions = LoadxException.class)
    public void testExtractResourceToStringThrowsAnExceptionWhenIncorrectPathPassed() {
        FileUtils.extractResourceToString("wrongPath");
    }

    private static String getExpectedContent() {
        return "<!DOCTYPE html><html lang=\"en\"><head>    <meta charset=\"UTF-8\">    <title>Title</title></head>" +
                "<body></body></html>";
    }

}
