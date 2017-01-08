package com.ngload.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class HTMLGetter {
    public static final Logger logger = LogManager.getLogger(HTMLGetter.class.getName());
    private ClassLoader loader = this.getClass().getClassLoader();

    public String getBasicHTML() {
        return getHTMLFromResourceURL(loader.getResource("html/test.html"));
    }
    public String getLinksHTML() {
        return getHTMLFromResourceURL(loader.getResource("html/linksTest.html"));
    }

    private String getHTMLFromResourceURL(URL resource) {
        StringBuilder HTML = new StringBuilder();
        try {
            Scanner input = new Scanner(new File(resource.getFile()));
            while (input.hasNext()) {
                HTML.append(input.nextLine());
            }
            input.close();
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (Exception e) {
            logger.error(e);
        }
        return HTML.toString();
    }
}
