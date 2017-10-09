package com.ngload.application.appcore.util;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DurationTest {

    @DataProvider(name = "correctDurations")
    public Object[][] getCorrectDurations() {
        return new Object[][] {
                {"00:00:10", 1000},
                {"00:10:00", 60000},
                {"00:59:10", 354000},
                {"10:00:00", 3600000}
        };
    }

    @DataProvider(name = "incorrectDurations")
    public Object[][] getIncorrectDurations() {
        return new Object[][] {
                {"00:00:60"},
                {"00:60:00"},
                {"00:89:10"},
                {"10:60:00"}
        };
    }

    @Test(enabled = true, dataProvider = "correctDurations")
    public void testIfParseCorrectly(String durationStr, int expectedMilis) {
        Duration duration = new Duration(durationStr);
        Assert.assertEquals(expectedMilis, duration.getDuration());
    }

    @Test(enabled = true, dataProvider = "incorrectDurations", expectedExceptions = RuntimeException.class)
    public void testIfThrowsRException(String durationStr) {
        Duration duration = new Duration(durationStr);
    }
}
