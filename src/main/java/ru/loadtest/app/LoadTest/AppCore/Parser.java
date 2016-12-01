package ru.loadtest.app.LoadTest.AppCore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.jsoup.Jsoup.*;

public class Parser {
    public static final Logger logger = LogManager.getLogger(Parser.class.getName());

    public static List<Link> getLinksFromHTML(String HTMLString) {
        List<Link> listOfLinks = new ArrayList<>();
        Document html = parse(HTMLString);
        Elements links = html.select("a[href]");
        for (Element link : links) {
            String currentLink = link.attr("href");
            if (!currentLink.isEmpty() && isLink(currentLink)) {
                if (!hasSameLink(listOfLinks, currentLink)) {
                    listOfLinks.add(new Link(link.attr("href")));
                }
            }
        }
        return listOfLinks;
    }

    private static boolean isLink(String URL) {
        boolean isURLLink = true;
        if (URL.length() >= 5) {
            isURLLink = !URL.substring(0, 5).equals("//www");
        }
        return URL.charAt(0) != '#' && !URL.contains("mailto:") && isURLLink;
    }

    private static boolean hasSameLink(List<Link> list, String currentLink) {
        return  list.contains(new Link(currentLink));
    }
}
