package TestLoadTestApplication;

import TestLoadTestApplication.ResourceGetter.LinkGetter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import ru.loadtest.app.LoadTest.HTTPConnection.ConnectionAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ConnectionAPITest {
    public static final Logger logger = LogManager.getLogger(ConnectionAPITest.class.getName());
    private ConnectionAPI connectionAPI = new ConnectionAPI("http://test.link");
    private LinkGetter linkGetter = new LinkGetter();

    @Test
    public void isCorrectLinkFullLink(){
        assertTrue(getIsCorrectResult("FullLink"));
    }

    @Test
    public void isCorrectLinkRoot(){
        assertTrue(getIsCorrectResult("RootLink"));
    }

    @Test
    public void isCorrectLinkShort(){
        assertTrue(getIsCorrectResult("ShortLink"));
    }

    @Test
    public void isCorrectLinkShortDouble(){
        assertTrue(getIsCorrectResult("ShortLinkDouble"));
    }

    @Test
    public void isCorrectLinkShortDoubleHTML(){
        assertTrue(getIsCorrectResult("ShortLinkDoubleHTML"));
    }

    @Test
    public void isCorrectLinkShortDoublePHP(){
        assertTrue(getIsCorrectResult("ShortLinkDoublePHP"));
    }

    @Test
    public void isCorrectLinkShortSlash(){
        assertTrue(getIsCorrectResult("ShortLinkSlash"));
    }

    @Test
    public void isCorrectLinkShortSlashDouble(){
        assertTrue(getIsCorrectResult("ShortLinkSlashDouble"));
    }

    @Test
    public void isCorrectLinkShortSlashDoubleHTML(){
        assertTrue(getIsCorrectResult("ShortLinkSlashDoubleHTML"));
    }

    @Test
    public void isCorrectLinkShortSlashDoublePHP(){
        assertTrue(getIsCorrectResult("ShortLinkSlashDoublePHP"));
    }

    @Test
    public void isCorrectLinkSharp(){
        assertFalse(getIsCorrectResult("SharpLink"));
    }

    @Test
    public void isCorrectLinkWWW(){
        assertTrue(getIsCorrectResult("WWWLink"));
    }

    @Test
    public void isCorrectLinkHTTPWWW(){
        assertTrue(getIsCorrectResult("HTTPWWWLink"));
    }

    @Test
    public void isCorrectLinkMailto(){
        assertFalse(getIsCorrectResult("Mailto"));
    }

    @Test
    public void isCorrectLinkMoreLevel(){
        assertTrue(getIsCorrectResult("MoreLevelLink"));
    }

    @Test
    public void isCorrectLinkHTTPS(){
        assertTrue(getIsCorrectResult("HTTPSLink"));
    }

    @Test
    public void isCorrectLinkExternal(){
        assertFalse(getIsCorrectResult("ExternalLink"));
    }

    private Boolean getIsCorrectResult(String linkType){
        Boolean result = false;
        try {
            Method method = ConnectionAPI.class.getDeclaredMethod("isCorrectLink", String.class);
            method.setAccessible(true);
            result = (Boolean) method.invoke(connectionAPI, linkGetter.getLinkParam(linkType));

        } catch (NoSuchMethodException e) {
            logger.error(e);
        } catch (InvocationTargetException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
        return result;
    }
}
