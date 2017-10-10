package com.ngload.application.appcore.webcore;

import com.ngload.application.appcore.entities.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Deprecated should be removed and redesined
 * Parser class that provides web page parsing and getting links from URLs
 */
@Deprecated
public class Parser extends Thread {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());
    private static WebConnector connection;
    private static final long DEFAULT_TIMEOUT = 60000;
    private static long USER_TIMEOUT;

    public static void execute(long timeout) {
        int parsersCount = getParsersCount(timeout);
        initThreads(parsersCount);
        logger.info(parsersCount + " threads for Parser have been created and started");
    }

    private static void initThreads(int parsersCount) {
        List<Parser> parsers = new ArrayList<>();
        for (int i = 0; i < parsersCount; i++) {
            parsers.add(new Parser());
        }
        parsers.parallelStream().forEach(Thread::start);
    }

    private static int getParsersCount(long timeout) {
        long TIME = (timeout <= 0)? DEFAULT_TIMEOUT:timeout;
        return (TIME <= 10000)? 1:(int)TIME/10000;
    }

    @Override
    public void run() {
    }

    private void collectRandomLinks() {
        long startTime = System.currentTimeMillis();
        long currentTime = 0;

        while (currentTime < DEFAULT_TIMEOUT) {
            currentTime = System.currentTimeMillis() - startTime;
        }
    }

    public static List<Link> getLinksFromURL(String URL) {
        List<Link> links = new ArrayList<>();
        Elements elements = selectElements(URL, "a[href]");
        elements.forEach((Parser) -> addLink(links, elements.attr("href")));
        return links;
    }

    private static Elements selectElements(String URL, String cssQuery) {
        connection = new WebConnector(WebHelper.getWorkURL());
        Document document = Jsoup.parse(connection.getHtmlByURL(URL));
        return document.select(cssQuery);
    }

    private static void addLink(List<Link> links, String URL) {
        if (!URL.isEmpty() && isLink(URL) && !hasSameLink(links, URL)){
            links.add(new Link(URL));
        }
    }

    private static boolean isLink(String URL) {
        boolean isURLLink = true;
        if (URL.length() >= 5) {
            isURLLink = !URL.substring(0, 5).equals("//www");
        }
        return URL.charAt(0) != '#' && !URL.contains("mailto:") && isURLLink;
    }

    /**
     *
     * @param list
     * @param currentLink
     * @return true if list has same link
     */
    private static boolean hasSameLink(List<Link> list, String currentLink) {
        return  list.contains(new Link(currentLink));
    }
}
