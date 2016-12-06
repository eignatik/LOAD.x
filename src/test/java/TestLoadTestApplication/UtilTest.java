package TestLoadTestApplication;

import TestLoadTestApplication.ResourceGetter.LinkGetter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.loadtest.app.LoadTest.AppCore.Util;

import static org.junit.Assert.*;
import static ru.loadtest.app.LoadTest.AppCore.Util.*;

public class UtilTest {
    private static String URL = "http://test.link";

    @BeforeClass
    public static void setUtilURL() {
        setWorkURL(URL);
    }

    @Test
    public void isLinkInBaseDomainCorrectLink() {
        boolean result = isLinkInDomain("http://test.link/index");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainIncorrectLink() {
        boolean result = isLinkInDomain("http://another-site.link/index");
        assertFalse(result);
    }

    @Test
    public void isLinkBaseDomainSharp() {
        boolean result = isLinkInDomain("#");
        assertFalse(result);
    }

    @Test
    public void isLinkBaseDomainDollar() {
        boolean result = isLinkInDomain("$sgf");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainStartFromhttp() {
        boolean result = isLinkInDomain("httpget");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainStartFromwww() {
        boolean result = isLinkInDomain("wwwdfgdfg");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainShortSlashedLink() {
        boolean result = isLinkInDomain("/index");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainShortLink() {
        boolean result = isLinkInDomain("index");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainWWW() {
        boolean result = isLinkInDomain("www.test.link/index");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainLinkWithExt() {
        boolean result = isLinkInDomain("/index.html");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainHTTPS() {
        boolean result = isLinkInDomain("https://test.link/index");
        assertTrue(result);
    }

    @Test
    public void isLinkBaseDomainWWWAndHTTP() {
        boolean result = isLinkInDomain("http://www.test.link/index");
        assertTrue(result);
    }

    @Test
    public void isLinkContainProtocolsCorrectHTTP() {
        boolean result = isLinkContainProtocols("http://link.test/");
        assertTrue(result);
    }

    @Test
    public void isLinkContainProtocolsCorrectHTTPS() {
        boolean result = isLinkContainProtocols("https://link.test/");
        assertTrue(result);
    }

    @Test
    public void isLinkContainProtocolsIncorrect() {
        boolean result = isLinkContainProtocols("httpp://link.test/");
        assertFalse(result);
    }

    @Test
    public void isRemoveProtocolsCorrect() {
        assertEquals(removeProtocols("http://google.com/"), "google.com/");
        assertEquals(removeProtocols("https://google.com/"), "google.com/");
        assertEquals(removeProtocols("www.google.com/"), "google.com/");
        assertEquals(removeProtocols("http://www.google.com/"), "google.com/");
        assertEquals(removeProtocols("https://www.google.com/"), "google.com/");
    }

    @Test
    public void isShortLinkCorrect() {
        assertTrue(isShortLink("link/test"));
    }

    @Test
    public void isShortLinkIncorrect() {
        assertFalse(isShortLink("http://link.ru"));
        assertFalse(isShortLink("mailto:test@test.ru"));
    }

    @Test
    public void isShortLinkWithoutSlashCorrect() {
        assertTrue(isShortLinkWithoutSlash("link/without/firstslash"));
    }

    @Test
    public void isShortWithoutSlashIncorrect() {
        assertFalse(isShortLinkWithoutSlash("/link/with/firstslash"));
    }
}
