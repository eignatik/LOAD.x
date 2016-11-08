package TestLoadTestApplication;

import TestLoadTestApplication.ResourceGetter.HTMLGetter;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.loadtest.app.LoadTest.AppCore.Parser.*;

public class ParserTest {
    private TestLoadTestApplication.ResourceGetter.HTMLGetter HTMLGetter = new HTMLGetter();

    @Test
    public void parseLinkWithCorrectView(){
        String result = getLinksFromHTML(HTMLGetter.getCorrectViewHTML()).get(0);
        assertEquals(result, "\"Link text\": \"http://testlink.zone\", ");
    }

    @Test
    public void parseLinkWithoutHref(){
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithoutHref()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithEmptyHref(){
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithEmptyHref()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithHrefSharp(){
        boolean result = getLinksFromHTML(HTMLGetter.getLinkWithHrefSharp()).isEmpty();
        assertTrue(result);
    }

    @Test
    public void parseLinkWithShortLink(){
        String result = getLinksFromHTML(HTMLGetter.getShortLinkHTML()).get(0);
        assertEquals(result, "\"Link text\": \"/testlink.zone\", ");
    }

    @Test
    public void parseLinkFromBigHtml(){
        String result = getLinksFromHTML(HTMLGetter.getLinkFromBigHTML()).get(0);
        assertEquals(result, "\"Text\": \"link\", ");
    }

    @Test
    public void parseLinksFromBigHtml(){
        Collection<String> list = getLinksFromHTML(HTMLGetter.getLinksFromBigHTML());
        assertTrue(list.size() == 4);
        for(String result : list){
            assertEquals(result, "\"Text\": \"link\", ");
        }
    }

    @Test
    public void parseLinkWithDifficultLinkTag(){
        String result = getLinksFromHTML(HTMLGetter.getDifficultLinkHTML()).get(0);
        assertEquals(result, "\"Text\": \"http://testlink.zone\", ");
    }

    //TODO Uncomment test and refactor


    @Test
    public void parseLinkWithWrongTag(){
        assertTrue(getLinksFromHTML(HTMLGetter.getWrongLinkHTML()).isEmpty());
    }

    @Test
    public void pareLinksWithWrongTags(){
        assertTrue(getLinksFromHTML(HTMLGetter.getWrongLinksHTML()).isEmpty());
    }

    @Test
    public void parseLinksFromRealPage(){
        Collection<String> list = getLinksFromHTML(HTMLGetter.getLinksFromRealPage());
        assertTrue(list.size() == 9);
    }
}
