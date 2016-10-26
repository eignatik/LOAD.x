package TestLoadTestApplication;

import org.junit.Assert;
import org.junit.Test;
import ru.loadtest.app.LoadTest.Pasrser.Parser;

public class ParserTest {
    @Test
    public void parseLinkWithCorrectView(){
        String HTML = "<a href=\"http://testlink.zone\">Link text</a>";
        String result;
        Parser parser = new Parser();
        parser.parseLinks(HTML);
        result = parser.getListOfLinks().get(0);
        Assert.assertEquals(result, "\"Link text\":\u0020\"http://testlink.zone\",\u0020");
    }

    @Test
    public void parseLinkWithShortLink(){
        String HTML = "<a href=\"/testlink.zone\">Link text</a>";
        String result;
        Parser parser = new Parser();
        parser.parseLinks(HTML);
        result = parser.getListOfLinks().get(0);
        Assert.assertEquals(result, "\"Link text\":\u0020\"/testlink.zone\",\u0020");
    }

    @Test
    public void parseLinkFromBigHtml(){
        String HTML = "<!DOCTYPE html>"
                            + "<html>"
                            + "<head>"
                            + "<title>Example</title>"
                            + "</head>"
                            + "<body>"
                            + "<a href=\"link\">Text</a>"
                            + "</table>"
                            + "</body>"
                            + "</html>";
        String result;
        Parser parser = new Parser();
        parser.parseLinks(HTML);
        result = parser.getListOfLinks().get(0);
        Assert.assertEquals(result, "\"Text\":\u0020\"link\",\u0020");
    }

    @Test
    public void parseLinksFromBigHtml(){
        String HTML = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>Example</title>"
                + "</head>"
                + "<body>"
                + "<a href=\"link\">Text</a>"
                + "<a href=\"link\">Text</a>"
                + "<a href=\"link\">Text</a>"
                + "<a href=\"link\">Text</a>"
                + "</table>"
                + "</body>"
                + "</html>";
        Parser parser = new Parser();
        parser.parseLinks(HTML);
        for(String result : parser.getListOfLinks()){
            Assert.assertEquals(result, "\"Text\":\u0020\"link\",\u0020");
        }
    }

    @Test
    public void parseLinkWithDifficultLinkTag(){
        String HTML = "<a href=\"http://testlink.zone\" class=\"class class2\"><div><span>Text</span></div></a>";
        String result;
        Parser parser = new Parser();
        parser.parseLinks(HTML);
        result = parser.getListOfLinks().get(0);
        Assert.assertEquals(result, "\"Text\":\u0020\"http://testlink.zone\",\u0020");
    }
}
