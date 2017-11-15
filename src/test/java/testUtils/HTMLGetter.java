package testUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class HTMLGetter {
    public static final Logger logger = LogManager.getLogger(HTMLGetter.class.getName());
    private static ClassLoader loader = HTMLGetter.class.getClassLoader();

    public static String getHTML(String path) {
        return getHTMLFromResourceURL(loader.getResource(path));
    }

    private static String getHTMLFromResourceURL(URL resource) {
        StringBuilder HTML = new StringBuilder();
        try (Scanner input = new Scanner(new File(resource.getFile()))) {
            while (input.hasNext()) {
                HTML.append(input.nextLine());
            }
        } catch (IOException e) {
            logger.error(e);
        }
        return HTML.toString();
    }
}
