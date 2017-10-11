package com.ngload.application.appcore.webcore.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Parser of links on the pages to provide map of links for execute testing
 */
public class LinkParser {
    private static final Logger logger = LogManager.getLogger(LinkParser.class.getName());
    /**
     * Default timeout in ms
     */
    private static final int DEFAULT_TIMEOUT = 60000;
    private int customTimeout;
    private URL startUrl;

    public LinkParser withTimeout(int customTimeout) {
        this.customTimeout = customTimeout;
        return this;
    }

    public LinkParser withStartUrl(URL startUrl) {
        this.startUrl = startUrl;
        return this;
    }

    public LinkParser withStartUrl(String startUrl) {
        try {
            this.startUrl = new URL(startUrl);
        } catch (MalformedURLException e) {
            logger.error(e);
        }
        return this;
    }

    public void parse() {
        //TODO: implement parsing logic
    }

    public static int getDefaultTimeout() {
        return DEFAULT_TIMEOUT;
    }

    public int getCustomTimeout() {
        return customTimeout;
    }

    public URL getStartUrl() {
        return startUrl;
    }
}
