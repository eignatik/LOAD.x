package TestLoadTestApplication;

import TestLoadTestApplication.ResourceGetter.HTMLGetter;
import org.junit.Test;
import ru.loadtest.app.LoadTest.Pasrser.Parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    private Parser parser = new Parser();
    private TestLoadTestApplication.ResourceGetter.HTMLGetter HTMLGetter = new HTMLGetter();

    @Test
    public void parseLinkWithCorrectView(){
        parser.parseLinks(HTMLGetter.getCorrectViewHTML());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Link text\": \"http://testlink.zone\", ");
    }

    @Test
    public void parseLinkWithoutHref(){
        parser.parseLinks(HTMLGetter.getLinkWithoutHref());
        assertTrue(parser.getListOfLinks().isEmpty());
    }

    @Test
    public void parseLinkWithEmptyHref(){
        parser.parseLinks(HTMLGetter.getLinkWithEmptyHref());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Text\": \"\", ");
    }

    @Test
    public void parseLinkWithHrefSharp(){
        parser.parseLinks(HTMLGetter.getLinkWithHrefSharp());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Text\": \"#\", ");
    }

    @Test
    public void parseLinkWithShortLink(){
        parser.parseLinks(HTMLGetter.getShortLinkHTML());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Link text\": \"/testlink.zone\", ");
    }

    @Test
    public void parseLinkFromBigHtml(){
        parser.parseLinks(HTMLGetter.getLinkFromBigHTML());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Text\": \"link\", ");
    }

    @Test
    public void parseLinksFromBigHtml(){
        parser.parseLinks(HTMLGetter.getLinksFromBigHTML());
        assertTrue(parser.getListOfLinks().size() == 4);
        for(String result : parser.getListOfLinks()){
            assertEquals(result, "\"Text\": \"link\", ");
        }
    }

    @Test
    public void parseLinkWithDifficultLinkTag(){
        parser.parseLinks(HTMLGetter.getDifficultLinkHTML());
        String result = parser.getListOfLinks().get(0);
        assertEquals(result, "\"Text\": \"http://testlink.zone\", ");
    }

    @Test
    public void parseLinkWithWrongTag(){
        parser.parseLinks(HTMLGetter.getWrongLinkHTML());
        assertTrue(parser.getListOfLinks().isEmpty());
    }

    @Test
    public void pareLinksWithWrongTags(){
        parser.parseLinks(HTMLGetter.getWrongLinksHTML());
        assertTrue(parser.getListOfLinks().isEmpty());
    }

    @Test
    public void parseLinksFromRealPage(){
        parser.parseLinks(HTMLGetter.getLinksFromRealPage());
        assertTrue(parser.getListOfLinks().size() == 24);
    }

}
