package TestLoadTestApplication;

import TestLoadTestApplication.ResourceGetter.HTMLGetter;
import org.junit.Test;
import ru.loadtest.app.LoadTest.AppCore.Link;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.loadtest.app.LoadTest.AppCore.Parser.*;

public class ParserTest {
    private TestLoadTestApplication.ResourceGetter.HTMLGetter HTMLGetter = new HTMLGetter();

    @Test
    public void parseLinkWithCorrectView() {
        String result = getLinksFromHTML(HTMLGetter.getCorrectViewHTML()).get(0).getURL();
        assertEquals(result, "http://testlink.zone");
    }

    @Test
    public void parseLinkWithoutHref() {
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithoutHref()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithEmptyHref() {
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithEmptyHref()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithHrefSharp() {
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithHrefSharp()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithShortLink() {
        String result = getLinksFromHTML(HTMLGetter.getShortLinkHTML()).get(0).getURL();
        assertEquals(result, "/testlink.zone");
    }

    @Test
    public void parseLinkFromBigHtml() {
        String result = getLinksFromHTML(HTMLGetter.getLinkFromBigHTML()).get(0).getURL();
        assertEquals(result, "link");
    }

    @Test
    public void parseLinksFromBigHtml() {
        Collection<Link> list = getLinksFromHTML(HTMLGetter.getLinksFromBigHTML());
        assertTrue(list.size() == 1);
        for (Link result : list) {
            assertEquals(result.getURL(), "link");
        }
    }

    @Test
    public void parseLinkWithDifficultLinkTag() {
        String result = getLinksFromHTML(HTMLGetter.getDifficultLinkHTML()).get(0).getURL();
        assertEquals(result, "http://testlink.zone");
    }


    @Test
    public void parseLinkWithWrongTag() {
        assertTrue(getLinksFromHTML(HTMLGetter.getWrongLinkHTML()).isEmpty());
    }

    @Test
    public void pareLinksWithWrongTags() {
        assertTrue(getLinksFromHTML(HTMLGetter.getWrongLinksHTML()).isEmpty());
    }

    @Test
    public void parseLinksFromRealPage() {
        Collection<Link> list = getLinksFromHTML(HTMLGetter.getLinksFromRealPage());
        assertTrue(list.size() == 9);
    }
}
