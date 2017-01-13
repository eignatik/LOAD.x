package com.ngload.application.appcore.webcore;

import com.ngload.application.appcore.webcore.entities.Link;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Parser {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());
    private static WebConnector connection;
    private static String workURL;
    private static final long DEFAULT_TIMEOUT = 60000;

    public static List<Link> getLinksFromURL(String URL) {
        List<Link> links = new ArrayList<>();
        Elements elements = selectElements(URL, "a[href]");
        elements.stream().forEach((Parser) -> addLink(links, elements.attr("href")));
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
