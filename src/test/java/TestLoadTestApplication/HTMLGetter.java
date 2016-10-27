package TestLoadTestApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class HTMLGetter {

    public static final Logger logger = LogManager.getLogger(HTMLGetter.class.getName());

    private ClassLoader classLoader = this.getClass().getClassLoader();

    public String getCorrectViewHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/CorrectView.html"));
    }

    public String getShortLinkHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/ShortLink.html"));
    }

    public String getLinkFromBigHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/BigHTML.html"));
    }

    public String getLinksFromBigHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/LinksBigHTML.html"));
    }

    public String getDifficultLinkHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/DifficultLink.html"));
    }

    public String getWrongLinkHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/WrongLinkTag.html"));
    }

    public String getWrongLinksHTML(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/WrongLinks.html"));
    }

    public String getLinkWithoutHref(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/LinkWithoutHref.html"));
    }

    public String getLinkWithEmptyHref(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/LinkWithEmptyHref.html"));
    }

    public String getLinkWithHrefSharp() {
        return getHTMLFromResourceURL(classLoader.getResource("Parser/LinkWithHrefSharp.html"));
    }

    public String getLinksFromRealPage(){
        return getHTMLFromResourceURL(classLoader.getResource("Parser/RealPage.html"));
    }

    private String getHTMLFromResourceURL(URL resource){
        StringBuilder HTML = new StringBuilder();
        try {
            Scanner input = new Scanner(new File(resource.getFile()));
            while(input.hasNext()){
                HTML.append(input.nextLine());
            }
            input.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch(Exception e) {
            logger.error(e);
        }
        return HTML.toString();
    }
}
