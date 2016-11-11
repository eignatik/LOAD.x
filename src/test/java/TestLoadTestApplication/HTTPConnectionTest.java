package TestLoadTestApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.loadtest.app.LoadTest.HTTPConnection.ConnectionAPI;
import ru.loadtest.app.LoadTest.HTTPConnection.HTTPConnection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class HTTPConnectionTest {
    public static final Logger logger = LogManager.getLogger(HTTPConnectionTest.class.getName());
    private HTTPConnection httpConnection = new HTTPConnection();
    private ConnectionAPI connectionAPI = new ConnectionAPI("http://test.link");

    @Test
    public void appendFullPathFullLink(){
        assertEquals(getAppendedURL("http://test.link/test1"), "http://test.link/test1");
    }

    @Test
    public void appendFullPathShortLink(){
        assertEquals(getAppendedURL("link"), "http://test.link/link");
        assertEquals(getAppendedURL("/link"), "http://test.link/link");
    }

    private String getAppendedURL(String URL){
        String appendedURL = "";
        try {
            Method appendFullPath = HTTPConnection.class.getDeclaredMethod("appendFullPath", String.class);
            appendFullPath.setAccessible(true);
            appendedURL = (String) appendFullPath.invoke(httpConnection, URL);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return appendedURL;

    }
}
