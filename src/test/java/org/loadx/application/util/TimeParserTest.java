package org.loadx.application.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TimeParserTest {

    @DataProvider(name = "timesCorrect")
    public Object[][] getCorrectTimes() {
        return new Object[][] {
                {"2h 10m", 7_800_000},
                {"10m", 600_000},
                {"2h", 7_200_000}
        };
    }

    @Test(dataProvider = "timesCorrect")
    public void testParseTime(String timeToParse, int expected) {
        Assert.assertEquals(TimeParser.parseTime(timeToParse), expected);
    }

    @DataProvider(name = "wrongTime")
    public Object[][] getWrongTimes() {
        return new Object[][] {
                {""},
                {null},
                {"h m"},
                {"m"},
                {"10 20"},
                {""},
                {"3d 10h 10m"}
        };
    }

    @Test(dataProvider = "wrongTime", expectedExceptions = IllegalArgumentException.class)
    public void testParseTimeWithWrongTimes(String timeToParse) {
        TimeParser.parseTime(timeToParse);
    }

}