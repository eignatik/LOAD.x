package com.ngload.application.appcore.webcore;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WebHelperTest {

    @DataProvider
    public Object[][] prepareWorkURLS() {
        return new Object[][]{
                {"http://localhost:8802", "http://localhost:8802/"},
                {"http://localhost:8802/", "http://localhost:8802/"},
                {"http://localhost:8802//", "http://localhost:8802/"},
                {"http://localhost:8802///", "http://localhost:8802/"},
                {"http://test.ru/", "http://test.ru/"}
        };
    }

    @Test(dataProvider = "prepareWorkURLS")
    public void getWorkURLTest(String actual, String expected) {
        WebHelper.setWorkURL(actual);
        Assert.assertEquals(WebHelper.getWorkURL(), expected);
    }
}
