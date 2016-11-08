package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionAPI {
    public static final Logger logger = LogManager.getLogger(ConnectionAPI.class.getName());

    /**
     * method that starts exploring the site from address.
     * @param address address that will be first link address in exploring. Empty quotes mean start from base URL
     */
    public void exploreLinks(String address, String baseURL) {
        HTTPConnection connection = new HTTPConnection(baseURL);
        Util.setWorkURL(baseURL);

    }
}
